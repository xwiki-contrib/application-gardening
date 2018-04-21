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

import javax.script.ScriptContext;

import org.xwiki.component.wiki.WikiComponent;
import org.xwiki.component.wiki.WikiComponentScope;
import org.xwiki.contrib.gardening.scripts.GardeningActionScript;
import org.xwiki.contrib.gardening.scripts.GardeningScriptException;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.script.ScriptContextManager;
import org.xwiki.template.Template;
import org.xwiki.template.TemplateManager;

import com.xpn.xwiki.objects.BaseObject;

/**
 * This is the default implementation of {@link GardeningActionScript} for being used with wiki components.
 *
 * @version $Id$
 * @since 1.0
 */
public class DefaultGardeningActionScript implements GardeningActionScript, WikiComponent
{
    private static final String DOCUMENT_BINDING_NAME = "documentReference";

    private EntityReference entityReference;

    private DocumentReference authorReference;

    private String scriptIdentifier;

    private String scriptName;

    private String scriptDescription;

    private String scriptContent;

    private TemplateManager templateManager;

    private ScriptContextManager scriptContextManager;

    /**
     * Build a new {@link DefaultGardeningActionScript}.
     *
     * @param reference a reference to the document holding the script
     * @param authorReference a reference to the author of the document
     * @param baseObject the object holding the script
     * @param templateManager an instance of the template manager
     * @param scriptContextManager an instance of the script context manager
     * @throws GardeningScriptException if an error happens
     */
    public DefaultGardeningActionScript(EntityReference reference, DocumentReference authorReference,
            BaseObject baseObject, TemplateManager templateManager,
            ScriptContextManager scriptContextManager) throws GardeningScriptException
    {
        this.entityReference = reference;
        this.authorReference = authorReference;
        this.setProperties(baseObject);
        this.templateManager = templateManager;
        this.scriptContextManager = scriptContextManager;
    }

    private void setProperties(BaseObject baseObject) throws GardeningScriptException
    {
        try {
            scriptIdentifier = baseObject.getStringValue("id");
            scriptName = baseObject.getStringValue("name");
            scriptDescription = baseObject.getStringValue("description");
            scriptContent = baseObject.getStringValue("content");
        } catch (Exception e) {
            throw new GardeningScriptException(
                    String.format("Failed to extract the parameters of the [%s] GardeningActionScriptClass.",
                            baseObject), e);
        }
    }

    @Override
    public DocumentReference getDocumentReference()
    {
        return (DocumentReference) entityReference.getParent();
    }

    @Override
    public EntityReference getEntityReference()
    {
        return entityReference;
    }

    @Override
    public DocumentReference getAuthorReference()
    {
        return authorReference;
    }

    @Override
    public Type getRoleType()
    {
        return GardeningActionScript.class;
    }

    @Override
    public String getRoleHint()
    {
        return scriptIdentifier;
    }

    @Override
    public WikiComponentScope getScope()
    {
        return WikiComponentScope.WIKI;
    }

    @Override
    public void execute(DocumentReference reference) throws GardeningScriptException
    {
        ScriptContext currentScriptContext = scriptContextManager.getCurrentScriptContext();
        currentScriptContext.setAttribute(DOCUMENT_BINDING_NAME, reference, ScriptContext.ENGINE_SCOPE);

        try {
            Template template = templateManager.createStringTemplate(scriptContent, authorReference);
            templateManager.execute(template);
        } catch (Exception e) {
            throw new GardeningScriptException(
                    String.format("Failed to evaluate the script [%s]", scriptIdentifier), e);
        } finally {
            currentScriptContext.removeAttribute(DOCUMENT_BINDING_NAME, ScriptContext.ENGINE_SCOPE);
        }
    }

    @Override
    public String getIdentifier()
    {
        return scriptIdentifier;
    }

    @Override
    public String getName()
    {
        return scriptName;
    }

    @Override
    public String getDescription()
    {
        return scriptDescription;
    }
}
