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

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.gardening.AbstractTestGardeningJob;
import org.xwiki.model.reference.DocumentReference;

/**
 * This is an implementaion of {@link org.xwiki.contrib.gardening.AbstractGardeningJob} used for testing purposes only.
 * This job calls all the selected query jobs, but will not call the action scripts on the returned documents.
 *
 * @version $Id$
 * @since 1.3
 */
@Component
@Named(AbstractTestGardeningJob.JOB_TYPE)
public class DefaultTestGardeningJob extends AbstractTestGardeningJob
{
    @Inject
    private GardeningJobHelper gardeningJobHelper;

    @Override
    protected void runInternal() throws Exception
    {
        logger.info("Starting test gardening job ; no action will be taken ...");
        Set<DocumentReference> foundDocuments = gardeningJobHelper.findDocuments(request);

        logger.info("{} documents found, in a real case the job [{}] would be triggered for each document at "
                        + "this point", foundDocuments.size(), request.getActionJobType());

        for (DocumentReference document : foundDocuments) {
            logger.info("Found document [{}]", document);
        }
    }
}
