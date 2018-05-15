/**
 * This file Copyright (c) 2015-2018 Magnolia International
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
package com.albertosoto.magnolia.bigdata.config;

import info.magnolia.dam.templating.functions.DamTemplatingFunctions;
import info.magnolia.imaging.functions.ImagingTemplatingFunctions;
import info.magnolia.module.blossom.view.JspTemplateViewRenderer;
import info.magnolia.module.blossom.view.TemplateViewResolver;
import info.magnolia.module.site.functions.SiteFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for using JSP views.
 */
@Configuration
public class JspRenderingConfiguration {

    /**
     * View resolver for JSP views.
     */
    @Bean
    public TemplateViewResolver jspTemplateViewResolver(JspTemplateViewRenderer viewRenderer) {
        TemplateViewResolver resolver = new TemplateViewResolver();
        resolver.setOrder(2);
        resolver.setPrefix("/templates/bigDataModule/");
        resolver.setViewNames("*.jsp");
        resolver.setViewRenderer(viewRenderer);
        return resolver;
    }

    @Bean
    public JspTemplateViewRenderer jspTemplateViewRenderer() {
        JspTemplateViewRenderer viewRenderer = new JspTemplateViewRenderer();
        viewRenderer.addContextAttribute("damfn", DamTemplatingFunctions.class);
        viewRenderer.addContextAttribute("imgfn", ImagingTemplatingFunctions.class);
        viewRenderer.addContextAttribute("sitefn", SiteFunctions.class);
/*
        Uncomment these if you're using MTE, Categorization or the REST module.

        viewRenderer.addContextAttribute("searchfn", info.magnolia.templating.functions.SearchTemplatingFunctions.class);
        viewRenderer.addContextAttribute("catfn", info.magnolia.module.categorization.functions.CategorizationTemplatingFunctions.class);
        viewRenderer.addContextAttribute("restfn", info.magnolia.resteasy.client.functions.RestTemplatingFunctions.class);
*/
        return viewRenderer;
    }
}
