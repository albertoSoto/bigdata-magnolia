/**
 * This file Copyright (c) 2013-2018 Magnolia International
 * Ltd.  (http://www.magnolia-cms.com). All rights reserved.
 *
 *
 * This file is dual-licensed under both the Magnolia
 * Network Agreement and the GNU General Public License.
 * You may elect to use one or the other of these licenses.
 *
 * This file is distributed in the hope that it will be
 * useful, but AS-IS and WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE, or NONINFRINGEMENT.
 * Redistribution, except as permitted by whichever of the GPL
 * or MNA you select, is prohibited.
 *
 * 1. For the GPL license (GPL), you can redistribute and/or
 * modify this file under the terms of the GNU General
 * Public License, Version 3, as published by the Free Software
 * Foundation.  You should have received a copy of the GNU
 * General Public License, Version 3 along with this program;
 * if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * 2. For the Magnolia Network Agreement (MNA), this file
 * and the accompanying materials are made available under the
 * terms of the MNA which accompanies this distribution, and
 * is available at http://www.magnolia-cms.com/mna.html
 *
 * Any modifications to this file must keep this entire header
 * intact.
 *
 */
package com.albertosoto.magnolia.bigdata.field.factory;

import com.albertosoto.magnolia.bigdata.field.ConsoleOutputField;
import com.albertosoto.magnolia.bigdata.field.definition.ConsoleOutputFieldDefinition;
import com.vaadin.v7.data.Item;
import com.vaadin.v7.ui.Field;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.form.field.definition.FieldDefinition;
import info.magnolia.ui.form.field.factory.AbstractFieldFactory;
import info.magnolia.ui.framework.message.MessagesManager;

import javax.inject.Inject;

/**
 * Creates and initializes a {@link ConsoleOutputField} field based on a field definition.
 * 
 * @param <D> definition type
 */
public class ConsoleOutputFieldFactory<D extends FieldDefinition> extends AbstractFieldFactory<ConsoleOutputFieldDefinition, String> {

    private ConsoleOutputField outputField;
    private Item relatedFieldItem;
    private SimpleTranslator simpleTranslator;
    private MessagesManager messagesManager;

    @Inject
    public ConsoleOutputFieldFactory(ConsoleOutputFieldDefinition definition, Item relatedFieldItem, SimpleTranslator simpleTranslator, MessagesManager messagesManager) {
        super(definition, relatedFieldItem);
        this.relatedFieldItem = relatedFieldItem;
        this.simpleTranslator = simpleTranslator;
        this.messagesManager = messagesManager;
    }

    /**
     * @deprecated since 2.4.6, please use {@link #ConsoleOutputFieldFactory(ConsoleOutputFieldDefinition, Item, SimpleTranslator, MessagesManager)}.
     */
    @Deprecated
    public ConsoleOutputFieldFactory(ConsoleOutputFieldDefinition definition, Item relatedFieldItem, SimpleTranslator simpleTranslator) {
        this(definition, relatedFieldItem, simpleTranslator, Components.getComponent(MessagesManager.class));
    }

    @Override
    protected Field<String> createFieldComponent() {
        outputField = new ConsoleOutputField(relatedFieldItem, simpleTranslator, messagesManager);
        return outputField;
    }

}
