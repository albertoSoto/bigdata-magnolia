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
package com.albertosoto.magnolia.bigdata.templates.components;

import info.magnolia.module.blossom.annotation.Area;
import info.magnolia.module.blossom.annotation.AvailableComponentClasses;
import info.magnolia.module.blossom.annotation.Template;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Component with two areas arranged as columns.
 */
@Controller
@Template(id="bigDataModule:components/twoColumn", title="Two column layout")
public class TwoColumnComponent {

    /**
     * Left column.
     */
    @Area("left")
    @Controller
    @AvailableComponentClasses({TextComponent.class, HighchartComponent.class,RengineResultComponent.class})
    public static class LeftArea {
        @RequestMapping("/twoColumn/left")
        public String render() {
            return "components/leftArea.jsp";
        }
    }

    /**
     * Right column.
     */
    @Area("right")
    @Controller
    @AvailableComponentClasses({TextComponent.class, HighchartComponent.class,RengineResultComponent.class})
    public static class RightArea {
        @RequestMapping("/twoColumn/right")
        public String render() {
            return "components/rightArea.jsp";
        }
    }

    @RequestMapping("/twoColumn")
    public String render() {
        return "components/twoColumns.jsp";
    }
}
