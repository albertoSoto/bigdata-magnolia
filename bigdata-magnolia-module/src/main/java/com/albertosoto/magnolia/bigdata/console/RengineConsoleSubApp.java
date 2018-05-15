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
import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;
import info.magnolia.ui.api.app.SubAppContext;
import info.magnolia.ui.framework.app.BaseSubApp;

import javax.inject.Inject;

/**
 * RengineConsoleSubApp.
 */
public class RengineConsoleSubApp extends BaseSubApp {

    @Inject
    public RengineConsoleSubApp(SubAppContext subAppContext, RengineConsoleView view) {
        super(subAppContext, view);
    }

    @Override
    protected void onSubAppStart() {
        super.onSubAppStart();
        //MgnlContext.setAttribute(RTerminal.BINDING_SESSION_ATTRIBUTE, new RTerminal.SerializableBinding(), Context.SESSION_SCOPE);
    }

    @Override
    protected void onSubAppStop() {
        super.onSubAppStop();
       // MgnlContext.removeAttribute(RTerminal.BINDING_SESSION_ATTRIBUTE, Context.SESSION_SCOPE);
    }
}