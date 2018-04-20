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

import org.xwiki.job.AbstractRequest;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.stability.Unstable;

/**
 * {@link org.xwiki.job.Request} for {@link AbstractGardeningActionJob}.
 *
 * @version $Id$
 * @since 1.0
 */
@Unstable
public class GardeningActionJobRequest extends AbstractRequest
{
    private DocumentReference actionDocument;

    /**
     * Constructs a new {@link GardeningActionJobRequest} while providing the document to act on.
     *
     * @param actionDocument the document to act on
     */
    public GardeningActionJobRequest(DocumentReference actionDocument)
    {
        super();
        this.actionDocument = actionDocument;
    }

    /**
     * Define the document that should be acted on by the job.
     *
     * @param actionDocument the document itself
     */
    public void setActionDocument(DocumentReference actionDocument)
    {
        this.actionDocument = actionDocument;
    }

    /**
     * @return the document that should be acted on
     */
    public DocumentReference getActionDocument()
    {
        return actionDocument;
    }
}
