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

import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.gardening.GardeningException;
import org.xwiki.contrib.gardening.script.GardeningScriptService;
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
@Named(WikiGardeningScriptService.ROLE_HINT)
@Unstable
public class WikiGardeningScriptService implements ScriptService
{
    /**
     * The component hint.
     */
    public static final String ROLE_HINT = GardeningScriptService.ROLE_HINT + ".wiki";

    @Inject
    private GardeningScriptService gardeningScriptService;

    /**
     * Start a new wiki gardening job.
     *
     * @return the job status of the global gardening job
     * @throws GardeningException if an error happened
     */
    public JobStatus start() throws GardeningException
    {
        return gardeningScriptService.start(
                Collections.singleton(WikiScriptGardeningQueryJob.JOB_TYPE),
                WikiScriptGardeningActionJob.JOB_TYPE);
    }
}
