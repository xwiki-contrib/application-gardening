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
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.wiki.WikiComponent;
import org.xwiki.component.wiki.WikiComponentException;
import org.xwiki.component.wiki.internal.bridge.WikiBaseObjectComponentBuilder;
import org.xwiki.contrib.gardening.GardeningException;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.model.reference.LocalDocumentReference;
import org.xwiki.script.ScriptContextManager;
import org.xwiki.security.authorization.AuthorizationManager;
import org.xwiki.security.authorization.Right;
import org.xwiki.template.TemplateManager;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.objects.BaseObject;

/**
 * Allow to register dynamically {@link DefaultGardeningActionScript} against the component manager using wiki
 * components.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(GardeningActionScriptComponentBuilder.BOUNDED_XOBJECT_CLASS)
@Singleton
public class GardeningActionScriptComponentBuilder implements WikiBaseObjectComponentBuilder
{
    /**
     * The XClass that should be used to build components.
     */
    public static final String BOUNDED_XOBJECT_CLASS = "Gardening.Code.GardeningActionScriptClass";

    @Inject
    private AuthorizationManager authorizationManager;

    @Inject
    private TemplateManager templateManager;

    @Inject
    private ScriptContextManager scriptContextManager;

    @Inject
    private Provider<XWikiContext> xWikiContextProvider;

    @Override
    public List<WikiComponent> buildComponents(BaseObject baseObject) throws WikiComponentException
    {
        XWikiDocument parentDocument = baseObject.getOwnerDocument();

        try {
            checkRights(parentDocument.getDocumentReference(), parentDocument.getAuthorReference());

            return Arrays.asList(
                    new DefaultGardeningActionScript(
                            baseObject.getReference(), parentDocument.getAuthorReference(), baseObject,
                            templateManager, scriptContextManager, xWikiContextProvider));
        } catch (Exception e) {
            throw new WikiComponentException(String.format("Failed to build the wiki component GardeningActionScript"
                    + " from [%s].", baseObject), e);
        }

    }

    @Override
    public EntityReference getClassReference()
    {
        return new LocalDocumentReference(Arrays.asList("Gardening", "Code"), "GardeningActionScriptClass");
    }

    /**
     * Ensure that the given author has the administrative rights in the current context.
     *
     * @param documentReference the working entity
     * @param authorReference the author that should have its rights checked
     * @throws GardeningException if the author rights are not sufficient
     */
    private void checkRights(DocumentReference documentReference, DocumentReference authorReference)
            throws GardeningException
    {
        if (!this.authorizationManager.hasAccess(Right.ADMIN, authorReference, documentReference.getWikiReference()))
        {
            throw new GardeningException("Registering gardening action scripts requires wiki administration rights.");
        }
    }
}
