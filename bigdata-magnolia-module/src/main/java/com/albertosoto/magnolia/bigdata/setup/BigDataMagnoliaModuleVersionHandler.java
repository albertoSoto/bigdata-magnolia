/**
 * This file Copyright (c) 2011-2018 Magnolia International
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
package com.albertosoto.magnolia.bigdata.setup;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.AbstractTask;
import info.magnolia.module.delta.Task;
import info.magnolia.module.delta.TaskExecutionException;
import info.magnolia.module.files.BasicFileExtractor;
import info.magnolia.module.files.ModuleFileExtractorTransformer;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * This class is optional and lets you manager the versions of your module,
 * by registering "deltas" to maintain the module's configuration, or other type of content.
 * If you don't need this, simply remove the reference to this class in the module descriptor xml.
 */
public class BigDataMagnoliaModuleVersionHandler extends DefaultModuleVersionHandler {

    protected List<Task> getStartupTasks(final InstallContext ctx) {

        // extracts files from mgnl-files on every startup - useful for development
        Task extractTask = new AbstractTask("Files extraction", "Extracts module files but don't perform any md5 checks.") {
            public void execute(InstallContext installContext) throws TaskExecutionException {
                final String moduleName = ctx.getCurrentModuleDefinition().getName();
                try {
                    new BasicFileExtractor().extractFiles(new ModuleFileExtractorTransformer(moduleName));
                } catch (IOException e) {
                    throw new TaskExecutionException("Could not extract files for module " + ctx.getCurrentModuleDefinition() + ": " + e.getMessage(), e);
                }
            }
        };
        return Collections.singletonList(extractTask);
    }
}
