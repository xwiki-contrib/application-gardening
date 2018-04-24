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

import java.util.Set;

import org.xwiki.component.annotation.Role;
import org.xwiki.contrib.gardening.GardeningException;

/**
 * Internal bridge allowing to access the wiki model without directly depending on the old core.
 *
 * @version $Id$
 * @since 1.0
 */
@Role
public interface ModelBridge
{
    /**
     * @return a list of active {@link org.xwiki.contrib.gardening.scripts.GardeningQueryScript}.
     * @throws GardeningException if an error happens
     */
    Set<String> getActiveQueryScripts() throws GardeningException;

    /**
     * @return the active {@link org.xwiki.contrib.gardening.scripts.GardeningActionScript}.
     * @throws GardeningException if an error happens
     */
    String getActiveActionScript() throws GardeningException;
}
