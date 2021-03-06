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
package org.xwiki.contrib.gardening.wiki;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.gardening.GardeningException;
import org.xwiki.contrib.gardening.script.GardeningScriptService;
import org.xwiki.contrib.gardening.scripts.GardeningActionScript;
import org.xwiki.contrib.gardening.scripts.GardeningQueryScript;
import org.xwiki.contrib.gardening.wiki.internal.WikiScriptGardeningActionJob;
import org.xwiki.contrib.gardening.wiki.internal.WikiScriptGardeningQueryJob;
import org.xwiki.job.event.status.JobStatus;
import org.xwiki.script.service.ScriptService;
import org.xwiki.stability.Unstable;

/**
 * Script service for the wiki module of the Gardening Application.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
@Named(GardeningScriptService.ROLE_HINT + "." + WikiGardeningScriptService.ROLE_HINT)
@Unstable
public class WikiGardeningScriptService implements ScriptService
{
    /**
     * The component hint.
     */
    public static final String ROLE_HINT = "wiki";

    @Inject
    @Named(GardeningScriptService.ROLE_HINT)
    private ScriptService gardeningScriptService;

    @Inject
    private WikiGardeningManager wikiGardeningManager;

    @Inject
    private WikiGardeningConfiguration wikiGardeningConfiguration;

    /**
     * Start a new wiki gardening job.
     *
     * @return the job status of the global gardening job
     * @throws GardeningException if an error happened
     */
    public JobStatus start() throws GardeningException
    {
        return ((GardeningScriptService) gardeningScriptService).start(
                Collections.singleton(WikiScriptGardeningQueryJob.JOB_TYPE),
                WikiScriptGardeningActionJob.JOB_TYPE);
    }

    /**
     * Start a new test wiki gardening job. This job will not act on the documents returned by the query scripts.
     *
     * @return the job status of the global gardening job
     * @throws GardeningException if an error happened
     */
    public JobStatus startTest() throws GardeningException
    {
        return ((GardeningScriptService) gardeningScriptService).startTest(
                Collections.singleton(WikiScriptGardeningQueryJob.JOB_TYPE),
                WikiScriptGardeningActionJob.JOB_TYPE);
    }

    /**
     * @return a list of available gardening query scripts
     * @throws GardeningException if an error happened
     */
    public List<GardeningQueryScript> getAvailableQueryScripts() throws GardeningException
    {
        return new ArrayList<>(wikiGardeningManager.getAvailableQueryScripts());
    }

    /**
     * @return a list of available gardening action scripts
     * @throws GardeningException if an error happened
     */
    public List<GardeningActionScript> getAvailableActionScripts() throws GardeningException
    {
        return new ArrayList<>(wikiGardeningManager.getAvailableActionScripts());
    }

    /**
     * @return the wiki gardening configuration component
     */
    public WikiGardeningConfiguration getConfiguration()
    {
        return wikiGardeningConfiguration;
    }
}
