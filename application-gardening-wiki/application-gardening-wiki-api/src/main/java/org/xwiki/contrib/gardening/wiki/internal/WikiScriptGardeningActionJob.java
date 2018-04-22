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

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.component.manager.ComponentManager;
import org.xwiki.contrib.gardening.AbstractGardeningActionJob;
import org.xwiki.contrib.gardening.GardeningException;
import org.xwiki.contrib.gardening.scripts.GardeningActionScript;
import org.xwiki.contrib.gardening.scripts.GardeningScriptException;

/**
 * This implementation of {@link AbstractGardeningActionJob} uses wiki-defined scripts to apply actions to the
 * given documents.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(WikiScriptGardeningActionJob.JOB_TYPE)
public class WikiScriptGardeningActionJob extends AbstractGardeningActionJob
{
    /**
     * The job type.
     */
    public static final String JOB_TYPE = "wikiScript" + AbstractGardeningActionJob.JOB_TYPE_SUFFIX;

    @Inject
    private ComponentManager componentManager;

    @Inject
    private ModelBridge modelBridge;

    @Inject
    private Logger logger;

    @Override
    protected void runInternal() throws Exception
    {
        String scriptId = modelBridge.getActiveActionScript();

        if (componentManager.hasComponent(GardeningActionScript.class, scriptId)) {
            try {
                GardeningActionScript script = componentManager.getInstance(GardeningActionScript.class, scriptId);
                script.execute(request.getActionDocument());
            } catch (ComponentLookupException e) {
                throw new GardeningException(String.format("Failed to retrieve GardeningActionScript component [%s]",
                        scriptId));
            } catch (GardeningScriptException e) {
                logger.error("Failed to execute action script [{}] : {}", scriptId, ExceptionUtils.getRootCause(e));
            }
        }
    }

    @Override
    public String getType()
    {
        return WikiScriptGardeningActionJob.JOB_TYPE;
    }
}
