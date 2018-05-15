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
 * <p>
 * Any modifications to this file must keep this entire header
 * intact.
 */
package com.albertosoto.magnolia.bigdata.field;

import com.albertosoto.magnolia.bigdata.spring.service.RenjinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.NativeButton;
import com.vaadin.v7.data.Item;
import com.vaadin.v7.data.Property;
import com.vaadin.v7.ui.CustomField;
import com.vaadin.v7.ui.TextArea;
import com.vaadin.v7.ui.VerticalLayout;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.ui.framework.message.MessagesManager;

/**
 * ConsoleOutputField.
 * info.magnolia.module.groovy.field.definition.ConsoleOutputFieldDefinition
 */
public class ConsoleOutputField extends CustomField<String> {

    private final VerticalLayout rootLayout = new VerticalLayout();
    private final TextArea outputArea = new TextArea();
    private final Button runButton = new NativeButton();
    private Item item;
    private SimpleTranslator simpleTranslator;
    private MessagesManager messagesManager;

    public ConsoleOutputField(Item item, SimpleTranslator simpleTranslator, MessagesManager messagesManager) {
        this.item = item;
        this.simpleTranslator = simpleTranslator;
        this.messagesManager = messagesManager;
    }

    @Override
    protected Component initContent() {
        setImmediate(true);
        rootLayout.setSizeFull();
        rootLayout.setSpacing(true);

        runButton.addStyleName("magnoliabutton");
        runButton.setCaption(simpleTranslator.translate("groovy.script.consoleOutput.run"));
        runButton.addClickListener(createButtonClickListener());

        rootLayout.addComponent(runButton);
        rootLayout.setExpandRatio(runButton, 0);
        rootLayout.setComponentAlignment(runButton, Alignment.MIDDLE_LEFT);

        outputArea.setImmediate(true);
        outputArea.addStyleName("console-output");
        outputArea.setWidth(100, Unit.PERCENTAGE);
        outputArea.setNullRepresentation("");
        outputArea.setNullSettingAllowed(true);

        rootLayout.addComponent(outputArea);

        return rootLayout;
    }

    @Override
    public String getValue() {
        return outputArea.getValue();
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

    private Button.ClickListener createButtonClickListener() {
        return new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Property<String> scriptText = item.getItemProperty("text");
                //runAsync(scriptText.getValue());
                try {
                    final String waitMessage = simpleTranslator.translate("groovy.script.consoleOutput.run.wait");
                    outputArea.setValue(waitMessage);
                    runButton.setEnabled(false);
                    RenjinService aux = new RenjinService();
                    outputArea.setValue(aux.executeRenjin(scriptText.getValue()));
                } catch (Exception e) {
                    outputArea.setValue(e.toString());
                }
                runButton.setEnabled(true);

            }
        };
    }
}
