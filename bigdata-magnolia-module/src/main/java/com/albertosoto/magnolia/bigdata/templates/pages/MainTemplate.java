/**
 * This file Copyright (c) 2010-2018 Magnolia International
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
package com.albertosoto.magnolia.bigdata.templates.pages;

import com.albertosoto.magnolia.bigdata.templates.components.HighchartComponent;
import com.albertosoto.magnolia.bigdata.templates.components.RengineResultComponent;
import com.albertosoto.magnolia.bigdata.templates.components.TextComponent;
import com.albertosoto.magnolia.bigdata.templates.components.TwoColumnComponent;
import info.magnolia.cms.core.MgnlNodeType;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.blossom.annotation.*;
import info.magnolia.ui.form.config.TabBuilder;
import info.magnolia.ui.framework.config.UiConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Template with two columns, a main content area and a right side column.
 */
@Controller
@Template(title = "Main", id = "bigDataModule:pages/main")
public class MainTemplate {

    /**
     * Main area.
     */
    @Area("main")
    @Controller
    @AvailableComponentClasses({TextComponent.class, TwoColumnComponent.class, HighchartComponent.class,RengineResultComponent.class})
    public static class MainArea {

        @RequestMapping("/mainTemplate/main")
        public String render() {
            return "pages/areas/main.ftl";
        }
    }

    /**
     * tobdone javadoc
     */
    @Controller
    @Area(value = "promos", title = "Promos")
    @Inherits
    @AvailableComponentClasses({TextComponent.class,TwoColumnComponent.class,HighchartComponent.class,RengineResultComponent.class})
    public static class PromosArea {

        @RequestMapping("/mainTemplate/promos")
        public String render() {
            return "pages/areas/promos.ftl";
        }
    }

    @RequestMapping("/mainTemplate")
    public String render(Node page, ModelMap model) throws RepositoryException {
        Map<String, String> navigation = new LinkedHashMap<String, String>();
        model.put("navigation", navigation);
        return "pages/main.ftl";
    }

    @TabFactory("Content")
    public void contentTab(UiConfig cfg, TabBuilder tab) {
        tab.fields(
                cfg.fields.text("title").label("Title"),
                cfg.fields.checkbox("hideInNavigation").label("Hide in navigation").description("Check this box to hide this page in navigation").buttonLabel("")
        );
    }
}
