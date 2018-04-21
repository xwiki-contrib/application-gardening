/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.contrib.gardening.wiki.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.component.manager.ComponentManager;
import org.xwiki.contrib.gardening.AbstractGardeningQueryJob;
import org.xwiki.contrib.gardening.scripts.GardeningQueryScript;
import org.xwiki.contrib.gardening.scripts.GardeningScriptException;
import org.xwiki.model.reference.DocumentReference;

/**
 * This component is responsible for running gardening query scripts defined in the wiki through wiki components.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(WikiScriptGardeningQueryJob.JOB_TYPE_PREFIX + AbstractGardeningQueryJob.JOB_TYPE_SUFFIX)
public class WikiScriptGardeningQueryJob extends AbstractGardeningQueryJob
{
    /**
     * The prefix of the job type.
     */
    public static final String JOB_TYPE_PREFIX = "wikiScript";

    @Inject
    private ModelBridge modelBridge;

    @Inject
    private ComponentManager componentManager;

    @Inject
    private Logger logger;

    @Override
    protected void runInternal() throws Exception
    {
        Set<String> scriptIds = modelBridge.getActiveQueryScripts();

        List<GardeningQueryScript> scripts = new ArrayList<>();
        for (String scriptId : scriptIds) {
            if (componentManager.hasComponent(GardeningQueryScript.class, scriptId)) {
                try {
                    scripts.add(componentManager.getInstance(GardeningQueryScript.class, scriptId));
                } catch (ComponentLookupException e) {
                    // This should not happen
                    logger.error("Failed to retrieve the GardeningQueryScript component [{}].", scriptId);
                }
            } else {
                logger.info("Skipping gardening query script [{}] as no corresponding component has been found.",
                        scriptId);
            }
        }

        Set<DocumentReference> foundDocuments = new HashSet<>();
        for (GardeningQueryScript script : scripts) {
            try {
                foundDocuments.addAll(script.execute());
            } catch (GardeningScriptException e) {
                logger.error("Failed to execute the gardening query script [{}]", script.getIdentifier());
            }
        }

        status.setFoundDocuments(foundDocuments);
    }

    @Override
    public String getType()
    {
        return WikiScriptGardeningQueryJob.JOB_TYPE_PREFIX + AbstractGardeningQueryJob.JOB_TYPE_SUFFIX;
    }
}
