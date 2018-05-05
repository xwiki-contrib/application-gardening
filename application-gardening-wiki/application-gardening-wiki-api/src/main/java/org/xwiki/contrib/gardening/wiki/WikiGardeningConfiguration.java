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

import java.util.List;

import org.xwiki.component.annotation.Role;
import org.xwiki.contrib.gardening.GardeningException;
import org.xwiki.stability.Unstable;

/**
 * Role responsible for defining the configuration values of the wiki gardening application.
 *
 * @version $Id$
 * @since 1.0
 */
@Role
@Unstable
public interface WikiGardeningConfiguration
{
    /**
     * @return a list of active gardening query scripts (by identifiers)
     * @throws GardeningException if an error happens
     */
    List<String> getActiveQueryScripts() throws GardeningException;

    /**
     * @return the active gardening action script identifier
     * @throws GardeningException if an error happens
     */
    String getActiveActionScript() throws GardeningException;

    /**
     * Set the list of gardening query scripts that should be active.
     *
     * @param activeQueryScripts a list of script identifiers
     * @throws GardeningException if an error happens
     */
    void setActiveQueryScripts(List<String> activeQueryScripts) throws GardeningException;

    /**
     * Same as {@link #setActiveQueryScripts(List)}.
     *
     * @param activeQueryScripts a list of script identifiers
     * @throws GardeningException if an error happens
     */
    void setActiveQueryScripts(String[] activeQueryScripts) throws GardeningException;

    /**
     * Set the gardening action script that should be active.
     *
     * @param activeActionScript the script identifier
     * @throws GardeningException if an error happens
     */
    void setActiveActionScript(String activeActionScript) throws GardeningException;
}
