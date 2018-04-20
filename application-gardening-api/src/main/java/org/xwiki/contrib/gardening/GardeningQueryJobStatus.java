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
package org.xwiki.contrib.gardening;

import java.util.Set;

import org.xwiki.job.DefaultJobStatus;
import org.xwiki.job.event.status.JobStatus;
import org.xwiki.logging.LoggerManager;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.observation.ObservationManager;
import org.xwiki.stability.Unstable;

/**
 * Define the {@link JobStatus} used by the {@link AbstractGardeningQueryJob}.
 *
 * @version $Id$
 * @since 1.0
 */
@Unstable
public class GardeningQueryJobStatus extends DefaultJobStatus<GardeningQueryJobRequest>
{
    private Set<DocumentReference> foundDocuments;

    /**
     * Builds a new {@link GardeningQueryJobStatus}.
     *
     * @param jobType the type of the job
     * @param request the job request
     * @param parentJobStatus the parent job status
     * @param observationManager the observation manager
     * @param loggerManager the logger manager
     */
    public GardeningQueryJobStatus(
            String jobType,
            GardeningQueryJobRequest request,
            JobStatus parentJobStatus,
            ObservationManager observationManager,
            LoggerManager loggerManager)
    {
        super(jobType, request, parentJobStatus, observationManager, loggerManager);
    }

    /**
     * @return a set of documents found during the job execution
     */
    public Set<DocumentReference> getFoundDocuments()
    {
        return this.foundDocuments;
    }

    /**
     * @param foundDocuments the documents found during the job execution
     */
    public void setFoundDocuments(Set<DocumentReference> foundDocuments)
    {
        this.foundDocuments = foundDocuments;
    }
}
