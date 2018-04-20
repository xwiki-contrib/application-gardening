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

import org.xwiki.job.AbstractRequest;
import org.xwiki.stability.Unstable;

/**
 * {@link org.xwiki.job.Request} for {@link AbstractGardeningJob}.
 *
 * @version $Id$
 * @since 1.0
 */
@Unstable
public class GardeningJobRequest extends AbstractRequest
{
    /**
     * The query job types to call in order to fetch a list of {@link org.xwiki.model.reference.DocumentReference}
     * that will then be handled by {@link AbstractGardeningActionJob}s.
     */
    private Set<String> queryJobTypes;

    /**
     * The action job type to use when applying an action to a document.
     */
    private String actionJobType;

    /**
     * @return a list of query jobs to use
     * @see #queryJobTypes
     */
    public Set<String> getQueryJobTypes()
    {
        return queryJobTypes;
    }

    /**
     * @param queryJobTypes the query jobs to use
     * @see #queryJobTypes
     */
    public void setQueryJobTypes(Set<String> queryJobTypes)
    {
        this.queryJobTypes = queryJobTypes;
    }

    /**
     * @return the action job to use
     * @see #actionJobType
     */
    public String getActionJobType()
    {
        return actionJobType;
    }

    /**
     * @param actionJobType the action job to use
     * @see #actionJobType
     */
    public void setActionJobType(String actionJobType)
    {
        this.actionJobType = actionJobType;
    }
}
