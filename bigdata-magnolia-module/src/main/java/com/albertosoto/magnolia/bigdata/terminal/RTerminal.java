/**
 * This file Copyright (c) 2013-2018 Magnolia International
 * Ltd.  (http://www.magnolia-cms.com). All rights reserved.
 * <p>
 * <p>
 * This file is dual-licensed under both the Magnolia
 * Network Agreement and the GNU General Public License.
 * You may elect to use one or the other of these licenses.
 * <p>
 * This file is distributed in the hope that it will be
 * useful, but AS-IS and WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE, or NONINFRINGEMENT.
 * Redistribution, except as permitted by whichever of the GPL
 * or MNA you select, is prohibited.
 * <p>
 * 1. For the GPL license (GPL), you can redistribute and/or
 * modify this file under the terms of the GNU General
 * Public License, Version 3, as published by the Free Software
 * Foundation.  You should have received a copy of the GNU
 * General Public License, Version 3 along with this program;
 * if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * <p>
 * 2. For the Magnolia Network Agreement (MNA), this file
 * and the accompanying materials are made available under the
 * terms of the MNA which accompanies this distribution, and
 * is available at http://www.magnolia-cms.com/mna.html
 * Any modifications to this file must keep this entire header
 * intact.
 */
package com.albertosoto.magnolia.bigdata.terminal;

import com.albertosoto.magnolia.bigdata.spring.service.RenjinService;
import com.google.gson.Gson;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.JavaScriptFunction;
import elemental.json.JsonArray;
import groovy.lang.GroovySystem;
import info.magnolia.context.MgnlContext;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.module.groovy.terminal.Terminal;
import info.magnolia.ui.framework.message.MessagesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Wraps JQuery RTerminal Emulator plugin (http://terminal.jcubic.pl) as Vaadin component. Loading jQuery from Google apis is needed when using the terminal component in the where jQuery can't be provided by Magnolia 5 framework.
 */
@JavaScript({"https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"
        , "jquery.terminal-0.7.12.custom.min.js"
        , "terminal_connector.js"})
@StyleSheet("bigDataModule/webresources/css/jquery.terminal.custom.css")
public class RTerminal extends Terminal {
    private final Gson gson = new Gson();
    private static final Logger log = LoggerFactory.getLogger(RTerminal.class);
    private SimpleTranslator simpleTranslator;
    private boolean useSystemContext;
    private Map<String, Set<String>> suggestions = new HashMap<String, Set<String>>();
    private boolean requiresNotificationMessageUponCompletion;
    private MessagesManager messagesManager;
    private ScriptEngine engine;
    StringWriter output = new StringWriter();
    StringWriter err = new StringWriter();

    public RTerminal(SimpleTranslator simpleTranslator, boolean useSystemContext, boolean requiresNotificationMessageUponCompletion, MessagesManager messagesManager) {
        super(simpleTranslator, useSystemContext, requiresNotificationMessageUponCompletion, messagesManager);
        this.simpleTranslator = simpleTranslator;
        this.useSystemContext = useSystemContext;
        this.messagesManager = messagesManager;
        this.getState().greetings = simpleTranslator.translate("r-console.greetings");
        this.requiresNotificationMessageUponCompletion = requiresNotificationMessageUponCompletion;
        this.getState().suggestions = gson.toJson(suggestions);
        setSizeFull();
        RenjinService service = new RenjinService();
        engine = service.getScriptEngine(  false);
        engine.getContext().setWriter(output);
        engine.getContext().setErrorWriter(err);
        addFunction("executeCommand", new JavaScriptFunction() {
            @Override
            public void call(JsonArray arguments) {
                getState().command = arguments.getString(0);
                resetState();
                try {
                    if (isUseSystemContext()) {
                        MgnlContext.doInSystemContext(new MgnlContext.Op<Void, Exception>() {
                            @Override
                            public Void exec() throws Exception {
                                output = new StringWriter();
                                engine.getContext().setWriter(output);
                                execute(getCommand());
                                return null;
                            }
                        });
                    } else {
                        execute(getCommand());
                    }
                } catch (Exception e) {
                    getState().output = e.getMessage();
                }
            }
        });

    }

    private void resetState() {
        getState().output = "";
        getState().view = "";
        getState().history = "";
        getState().inProgress = false;
    }

    public void execute(String command) throws Exception {
        log.debug("executing command [{}]", command);
        StringWriter sw = new StringWriter();
        getState().output = simpleTranslator.translate("r-script.script.consoleOutput.run.wait");
        try{
            engine.eval(command);
            getState().output = output.toString();
        }catch (Exception e){
            getState().output = e.toString();
        }

    }
}
