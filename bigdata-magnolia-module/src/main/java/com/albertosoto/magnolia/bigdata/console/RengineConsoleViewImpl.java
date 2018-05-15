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
package com.albertosoto.magnolia.bigdata.console;

import com.albertosoto.magnolia.bigdata.terminal.RTerminal;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.framework.message.MessagesManager;

import javax.inject.Inject;

/**
 * RengineConsoleViewImpl.
 */
public class RengineConsoleViewImpl implements RengineConsoleView {

    private CssLayout container = new CssLayout();

    @Inject
    public RengineConsoleViewImpl(SimpleTranslator simpleTranslator, MessagesManager messagesManager) {
        container.setSizeFull();
        container.addComponent(new RTerminal(simpleTranslator, false, true, messagesManager));
    }

    /**
     * @deprecated since 2.4.6, please use {@link #RengineConsoleViewImpl(SimpleTranslator, MessagesManager)} instead.
     */
    @Deprecated
    public RengineConsoleViewImpl(SimpleTranslator simpleTranslator) {
        this(simpleTranslator, Components.getComponent(MessagesManager.class));
    }

    @Override
    public Component asVaadinComponent() {
        return container;
    }

}
