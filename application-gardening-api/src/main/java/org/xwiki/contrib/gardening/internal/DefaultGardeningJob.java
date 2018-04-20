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
package org.xwiki.contrib.gardening.internal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.gardening.AbstractGardeningJob;
import org.xwiki.contrib.gardening.GardeningActionJobRequest;
import org.xwiki.contrib.gardening.GardeningQueryJobRequest;
import org.xwiki.contrib.gardening.GardeningQueryJobStatus;
import org.xwiki.job.Job;
import org.xwiki.job.JobExecutor;
import org.xwiki.model.reference.DocumentReference;

/**
 * This is the default implementation of the {@link AbstractGardeningJob}.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(AbstractGardeningJob.JOB_TYPE)
public class DefaultGardeningJob extends AbstractGardeningJob
{
    @Inject
    private JobExecutor jobExecutor;

    @Override
    protected void runInternal() throws Exception
    {
        Set<DocumentReference> foundDocuments = new HashSet<>();

        /* At some point, we should switch to pooled query execution as here we're using the full benefits
        of jobs */
        for (String queryJobType : request.getQueryJobTypes()) {
            GardeningQueryJobRequest queryJobRequest = new GardeningQueryJobRequest();
            queryJobRequest.setId(Arrays.asList(queryJobType, UUID.randomUUID().toString()));

            Job queryJob = jobExecutor.execute(queryJobType, queryJobRequest);
            queryJob.join();

            foundDocuments.addAll(((GardeningQueryJobStatus) queryJob.getStatus()).getFoundDocuments());
        }

        /* Same here */
        for (DocumentReference document : foundDocuments) {
            GardeningActionJobRequest actionJobRequest = new GardeningActionJobRequest(document);
            actionJobRequest.setId(Arrays.asList(request.getActionJobType(), UUID.randomUUID().toString()));

            Job actionJob = jobExecutor.execute(request.getActionJobType(), actionJobRequest);
            actionJob.join();
        }
    }
}
