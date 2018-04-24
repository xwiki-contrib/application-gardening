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
package org.xwiki.contrib.gardening.script;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.bridge.DocumentAccessBridge;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.gardening.AbstractGardeningJob;
import org.xwiki.contrib.gardening.GardeningException;
import org.xwiki.contrib.gardening.GardeningJobRequest;
import org.xwiki.job.Job;
import org.xwiki.job.JobException;
import org.xwiki.job.JobExecutor;
import org.xwiki.job.event.status.JobStatus;
import org.xwiki.model.ModelContext;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.script.service.ScriptService;
import org.xwiki.script.service.ScriptServiceManager;
import org.xwiki.security.authorization.AuthorizationManager;
import org.xwiki.security.authorization.Right;
import org.xwiki.stability.Unstable;

/**
 * Script service for the gardening application.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
@Named(GardeningScriptService.ROLE_HINT)
@Unstable
public class GardeningScriptService implements ScriptService
{
    /**
     * Hint of the component.
     */
    public static final String ROLE_HINT = "gardening";

    private JobStatus lastGardeningJobStatus;

    @Inject
    private ScriptServiceManager scriptServiceManager;

    @Inject
    private JobExecutor jobExecutor;

    @Inject
    private DocumentAccessBridge documentAccessBridge;

    @Inject
    private AuthorizationManager authorizationManager;

    @Inject
    private ModelContext modelContext;

    /**
     * Get a sub script service related to wiki. (Note that we're voluntarily using an API name of "get" to make it
     * extra easy to access Script Services from Velocity (since in Velocity writing <code>$services.wiki.name</code> is
     * equivalent to writing <code>$services.wiki.get("name")</code>). It also makes it a short and easy API name for
     * other scripting languages.
     *
     * @param serviceName id of the script service
     * @return the service asked or null if none could be found
     */
    public ScriptService get(String serviceName)
    {
        return scriptServiceManager.get(ROLE_HINT + '.' + serviceName);
    }

    /**
     * Start a new gardenig job.
     *
     * @param queryJobTypes a collection of job types that will be used for fetching a set of documents that need
     * to be gardened.
     * @param actionJobType the job type of the action to apply on the selected documents
     * @return the job status of the global gardening action
     * @throws GardeningException if an error happens
     */
    public JobStatus start(Collection<String> queryJobTypes, String actionJobType) throws GardeningException
    {
        DocumentReference currentUser = documentAccessBridge.getCurrentUserReference();

        if (!authorizationManager.hasAccess(Right.ADMIN, currentUser, modelContext.getCurrentEntityReference())) {
            throw new GardeningException("Starting a gardening job requires administrator privileges.");
        }

        // Convert the queryJobTypes to a set that can be transferred to the job request
        Set<String> queryJobTypesSet = new HashSet<>(queryJobTypes);

        GardeningJobRequest jobRequest = new GardeningJobRequest();
        jobRequest.setQueryJobTypes(queryJobTypesSet);
        jobRequest.setActionJobType(actionJobType);
        jobRequest.setId(Arrays.asList(AbstractGardeningJob.JOB_TYPE, UUID.randomUUID().toString()));

        try {
            Job job = jobExecutor.execute(AbstractGardeningJob.JOB_TYPE, jobRequest);

            lastGardeningJobStatus = job.getStatus();
            return lastGardeningJobStatus;
        } catch (JobException e) {
            throw new GardeningException(
                    String.format("Failed to start the gardening job [%s]", jobRequest.getId()), e);
        }
    }

    /**
     * @return the last gardening job status, or null if none has already been triggered
     */
    public JobStatus getLastGardeningJobStatus()
    {
        return lastGardeningJobStatus;
    }
}
