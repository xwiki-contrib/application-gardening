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

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.descriptor.ComponentDescriptor;
import org.xwiki.component.manager.ComponentManager;
import org.xwiki.contrib.gardening.GardeningException;
import org.xwiki.contrib.gardening.scripts.GardeningActionScript;
import org.xwiki.contrib.gardening.scripts.GardeningQueryScript;
import org.xwiki.contrib.gardening.wiki.WikiGardeningManager;

/**
 * This is the default implementation of {@link WikiGardeningManager}.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
public class DefaultWikiGardeningManager implements WikiGardeningManager
{
    @Inject
    @Named("wiki")
    private ComponentManager wikiComponentManager;

    @Override
    public Set<String> getAvailableQueryScripts() throws GardeningException
    {
        List<ComponentDescriptor<GardeningQueryScript>> scripts =
                wikiComponentManager.getComponentDescriptorList((Type) GardeningQueryScript.class);

        return scripts.stream().map(ComponentDescriptor::getRoleHint).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAvailableActionScripts() throws GardeningException
    {
        List<ComponentDescriptor<GardeningQueryScript>> scripts =
                wikiComponentManager.getComponentDescriptorList((Type) GardeningActionScript.class);

        return scripts.stream().map(ComponentDescriptor::getRoleHint).collect(Collectors.toSet());
    }
}
