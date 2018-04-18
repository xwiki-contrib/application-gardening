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

import org.xwiki.job.AbstractJob;
import org.xwiki.job.DefaultJobStatus;
import org.xwiki.stability.Unstable;

/**
 * The {@link AbstractGardeningJob} is responsible for supervising {@link AbstractGardeningQueryJob}s
 * and {@link AbstractGardeningActionJob}s.
 *
 * @version $Id$
 * @since 1.0
 */
@Unstable
public abstract class AbstractGardeningJob extends
        AbstractJob<GardeningJobRequest, DefaultJobStatus<GardeningJobRequest>>
{
    /**
     * The type of the job ; also used as a job identifier.
     */
    public static final String JOB_TYPE = "gardeningJob";

    @Override
    public String getType()
    {
        return JOB_TYPE;
    }
}
