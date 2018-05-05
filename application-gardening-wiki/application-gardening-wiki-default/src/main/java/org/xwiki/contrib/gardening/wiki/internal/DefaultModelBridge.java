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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.gardening.GardeningException;
import org.xwiki.model.reference.LocalDocumentReference;
import org.xwiki.text.StringUtils;

import com.xpn.xwiki.XWiki;
import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.objects.BaseObject;

/**
 * This is the default implementation of the model bridge, allowing access to the old core without adding a direct
 * dependency in application-gardening-wiki-api.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
public class DefaultModelBridge implements ModelBridge
{
    private static final List<String> GARDENING_CODE_SPACE = Arrays.asList("Gardening", "Code");

    private static final LocalDocumentReference GARDENING_SCRIPT_ADMINISTRATION = new LocalDocumentReference(
            GARDENING_CODE_SPACE, "GardeningScriptAdministration");

    private static final LocalDocumentReference GARDENING_SCRIPT_CONFIGURATION_CLASS = new LocalDocumentReference(
            GARDENING_CODE_SPACE, "GardeningScriptConfigurationClass");

    private static final String ACTIVE_QUERY_SCRIPTS = "activeQueryScripts";

    private static final String ACTIVE_ACTION_SCRIPT = "activeActionScript";

    @Inject
    private Provider<XWikiContext> xWikiContextProvider;

    @Override
    public Set<String> getActiveQueryScripts() throws GardeningException
    {
        try {
            BaseObject configuration = getGardeningScriptConfiguration();

            if (configuration != null) {
                Set<String> activeQueryScripts =
                        new HashSet<>((List<String>) configuration.getListValue(ACTIVE_QUERY_SCRIPTS));
                return activeQueryScripts;
            } else {
                return Collections.EMPTY_SET;
            }

        } catch (XWikiException e) {
            throw new GardeningException("Failed to get the active query scripts.", e);
        }
    }

    @Override
    public String getActiveActionScript() throws GardeningException
    {
        try {
            BaseObject configuration = getGardeningScriptConfiguration();

            if (configuration != null) {
                String activeActionScript = configuration.getStringValue(ACTIVE_ACTION_SCRIPT);
                return (activeActionScript == null) ? StringUtils.EMPTY : activeActionScript;
            } else {
                return StringUtils.EMPTY;
            }

        } catch (XWikiException e) {
            throw new GardeningException("Failed to get the active action script.", e);
        }
    }

    @Override
    public void setActiveQueryScripts(Set<String> activeQueryScripts) throws GardeningException
    {
        try {
            XWikiContext xWikiContext = xWikiContextProvider.get();
            BaseObject configuration = getGardeningScriptConfiguration();

            configuration.set(ACTIVE_QUERY_SCRIPTS,
                    String.join(",", activeQueryScripts), xWikiContext);
            xWikiContext.getWiki().saveDocument(getConfigurationDocument(), xWikiContext);
        } catch (XWikiException e) {
            throw new GardeningException("Failed to save the active query scripts.", e);
        }
    }

    @Override
    public void setActiveActionScript(String activeActionScript) throws GardeningException
    {
        try {
            XWikiContext xWikiContext = xWikiContextProvider.get();
            BaseObject configuration = getGardeningScriptConfiguration();

            configuration.set(ACTIVE_ACTION_SCRIPT, activeActionScript, xWikiContext);
            xWikiContext.getWiki().saveDocument(getConfigurationDocument(), xWikiContext);
        } catch (XWikiException e) {
            throw new GardeningException("Failed to save the active action script.", e);
        }
    }

    private XWikiDocument getConfigurationDocument() throws XWikiException
    {
        XWikiContext xWikiContext = xWikiContextProvider.get();
        XWiki xwiki = xWikiContext.getWiki();

        return xwiki.getDocument(GARDENING_SCRIPT_ADMINISTRATION, xWikiContext);
    }

    private BaseObject getGardeningScriptConfiguration() throws XWikiException
    {
        XWikiDocument document = getConfigurationDocument();
        return document.getXObject(GARDENING_SCRIPT_CONFIGURATION_CLASS, true, xWikiContextProvider.get());
    }
}
