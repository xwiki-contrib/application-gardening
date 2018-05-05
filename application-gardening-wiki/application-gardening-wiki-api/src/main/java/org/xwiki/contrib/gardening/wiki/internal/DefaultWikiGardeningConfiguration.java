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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.gardening.GardeningException;
import org.xwiki.contrib.gardening.wiki.WikiGardeningConfiguration;

/**
 * This is the default implementation of {@link WikiGardeningConfiguration}.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
public class DefaultWikiGardeningConfiguration implements WikiGardeningConfiguration
{
    @Inject
    private ModelBridge modelBridge;

    @Override
    public List<String> getActiveQueryScripts() throws GardeningException
    {
        return new ArrayList<>(modelBridge.getActiveQueryScripts());
    }

    @Override
    public String getActiveActionScript() throws GardeningException
    {
        return modelBridge.getActiveActionScript();
    }

    @Override
    public void setActiveQueryScripts(List<String> activeQueryScripts) throws GardeningException
    {
        modelBridge.setActiveQueryScripts(new HashSet<>(activeQueryScripts));
    }

    @Override
    public void setActiveQueryScripts(String[] activeQueryScripts) throws GardeningException
    {
        setActiveQueryScripts(Arrays.asList(activeQueryScripts));
    }

    @Override
    public void setActiveActionScript(String activeActionScript) throws GardeningException
    {
        modelBridge.setActiveActionScript(activeActionScript);
    }
}
