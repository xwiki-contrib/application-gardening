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
import java.util.Set;

import org.xwiki.component.wiki.WikiComponent;
import org.xwiki.component.wiki.WikiComponentScope;
import org.xwiki.contrib.gardening.scripts.GardeningQueryScript;
import org.xwiki.contrib.gardening.scripts.GardeningScriptException;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.EntityReference;

/**
 * Define a Gardening Query script that can be created through wiki component builders.
 *
 * @version $Id$
 * @since 1.0
 */
public class WikiGardeningQueryScript implements GardeningQueryScript, WikiComponent
{
    private String identifier;

    private String name;

    private String description;

    private EntityReference entityReference;

    private DocumentReference authorReference;

    @Override
    public Set<DocumentReference> execute() throws GardeningScriptException
    {
        return null;
    }

    @Override
    public String getIdentifier()
    {
        return null;
    }

    @Override
    public String getName()
    {
        return null;
    }

    @Override
    public String getDescription()
    {
        return null;
    }

    @Override
    public DocumentReference getDocumentReference()
    {
        return null;
    }

    @Override
    public DocumentReference getAuthorReference()
    {
        return authorReference;
    }

    @Override
    public Type getRoleType()
    {
        return GardeningQueryScript.class;
    }

    @Override
    public String getRoleHint()
    {
        return this.identifier;
    }

    @Override
    public WikiComponentScope getScope()
    {
        return WikiComponentScope.WIKI;
    }
}
