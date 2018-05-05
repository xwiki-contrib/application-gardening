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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.descriptor.ComponentDescriptor;
import org.xwiki.component.manager.ComponentLookupException;
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

    @Inject
    private Logger logger;

    @Override
    public Set<GardeningQueryScript> getAvailableQueryScripts() throws GardeningException
    {
        List<ComponentDescriptor<GardeningQueryScript>> scripts =
                wikiComponentManager.getComponentDescriptorList((Type) GardeningQueryScript.class);

        return scripts.stream()
                .map(x -> this.getComponent(GardeningQueryScript.class, x.getRoleHint()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<GardeningActionScript> getAvailableActionScripts() throws GardeningException
    {
        List<ComponentDescriptor<GardeningQueryScript>> scripts =
                wikiComponentManager.getComponentDescriptorList((Type) GardeningActionScript.class);

        return scripts.stream()
                .map(x -> this.getComponent(GardeningActionScript.class, x.getRoleHint()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * Will return the corresponding component of the injected component manager or log an exception.
     *
     * @param cls the class of the component to retrieve
     * @param roleHint the hint of the component
     * @param <T> the type of the component
     * @return the found component
     */
    private <T> T getComponent(Class<T> cls, String roleHint)
    {
        try {
            return wikiComponentManager.getInstance(cls, roleHint);
        } catch (ComponentLookupException e) {
            logger.error("Failed to fetch component [{}] with hint [{}] from the component manager.", cls, roleHint, e);
            return null;
        }
    }
}
