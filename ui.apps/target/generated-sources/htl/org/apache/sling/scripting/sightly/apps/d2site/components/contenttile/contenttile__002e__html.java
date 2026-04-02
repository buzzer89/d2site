/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ******************************************************************************/
package org.apache.sling.scripting.sightly.apps.d2site.components.contenttile;

import java.io.PrintWriter;
import java.util.Collection;
import javax.script.Bindings;

import org.apache.sling.scripting.sightly.render.RenderUnit;
import org.apache.sling.scripting.sightly.render.RenderContext;

public final class contenttile__002e__html extends RenderUnit {

    @Override
    protected final void render(PrintWriter out,
                                Bindings bindings,
                                Bindings arguments,
                                RenderContext renderContext) {
// Main Template Body -----------------------------------------------------------------------------

Object _global_clientlib = null;
Object _global_model = null;
_global_clientlib = renderContext.call("use", "/libs/granite/sightly/templates/clientlib.html", obj());
out.write("\n    ");
{
    Object var_templatevar0 = renderContext.getObjectModel().resolveProperty(_global_clientlib, "css");
    {
        String var_templateoptions1_field$_categories = "d2site.contenttile";
        {
            java.util.Map var_templateoptions1 = obj().with("categories", var_templateoptions1_field$_categories);
            callUnit(out, renderContext, var_templatevar0, var_templateoptions1);
        }
    }
}
out.write("\n\n");
_global_model = renderContext.call("use", com.d2.core.models.ContentTileModel.class.getName(), obj());
{
    Object var_testvariable2 = _global_model;
    if (renderContext.getObjectModel().toBoolean(var_testvariable2)) {
        out.write("<div data-cmp-is=\"contenttile\"");
        {
            Object var_attrvalue3 = renderContext.getObjectModel().resolveProperty(_global_model, "cssClass");
            {
                Object var_attrcontent4 = renderContext.call("xss", var_attrvalue3, "attribute");
                {
                    boolean var_shoulddisplayattr6 = (((null != var_attrcontent4) && (!"".equals(var_attrcontent4))) && ((!"".equals(var_attrvalue3)) && (!((Object)false).equals(var_attrvalue3))));
                    if (var_shoulddisplayattr6) {
                        out.write(" class");
                        {
                            boolean var_istrueattr5 = (var_attrvalue3.equals(true));
                            if (!var_istrueattr5) {
                                out.write("=\"");
                                out.write(renderContext.getObjectModel().toString(var_attrcontent4));
                                out.write("\"");
                            }
                        }
                    }
                }
            }
        }
        out.write(">\n\n    ");
        {
            Object var_testvariable7 = renderContext.getObjectModel().resolveProperty(_global_model, "iconPath");
            if (renderContext.getObjectModel().toBoolean(var_testvariable7)) {
                out.write("<div class=\"cmp-contenttile__icon\">\n        <img class=\"cmp-contenttile__icon-image\"");
                {
                    Object var_attrvalue8 = renderContext.getObjectModel().resolveProperty(_global_model, "iconPath");
                    {
                        Object var_attrcontent9 = renderContext.call("xss", var_attrvalue8, "uri");
                        {
                            boolean var_shoulddisplayattr11 = (((null != var_attrcontent9) && (!"".equals(var_attrcontent9))) && ((!"".equals(var_attrvalue8)) && (!((Object)false).equals(var_attrvalue8))));
                            if (var_shoulddisplayattr11) {
                                out.write(" src");
                                {
                                    boolean var_istrueattr10 = (var_attrvalue8.equals(true));
                                    if (!var_istrueattr10) {
                                        out.write("=\"");
                                        out.write(renderContext.getObjectModel().toString(var_attrcontent9));
                                        out.write("\"");
                                    }
                                }
                            }
                        }
                    }
                }
                {
                    Object var_attrvalue12 = ((renderContext.getObjectModel().toBoolean(renderContext.getObjectModel().resolveProperty(_global_model, "headline")) ? renderContext.getObjectModel().resolveProperty(_global_model, "headline") : "icon"));
                    {
                        Object var_attrcontent13 = renderContext.call("xss", var_attrvalue12, "attribute");
                        {
                            boolean var_shoulddisplayattr15 = (((null != var_attrcontent13) && (!"".equals(var_attrcontent13))) && ((!"".equals(var_attrvalue12)) && (!((Object)false).equals(var_attrvalue12))));
                            if (var_shoulddisplayattr15) {
                                out.write(" alt");
                                {
                                    boolean var_istrueattr14 = (var_attrvalue12.equals(true));
                                    if (!var_istrueattr14) {
                                        out.write("=\"");
                                        out.write(renderContext.getObjectModel().toString(var_attrcontent13));
                                        out.write("\"");
                                    }
                                }
                            }
                        }
                    }
                }
                out.write("/>\n    </div>");
            }
        }
        out.write("\n\n    ");
        {
            Object var_testvariable16 = renderContext.getObjectModel().resolveProperty(_global_model, "headline");
            if (renderContext.getObjectModel().toBoolean(var_testvariable16)) {
                out.write("<h3 class=\"cmp-contenttile__headline\">");
                {
                    String var_17 = (("\n        " + renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(_global_model, "headline"), "text"))) + "\n    ");
                    out.write(renderContext.getObjectModel().toString(var_17));
                }
                out.write("</h3>");
            }
        }
        out.write("\n\n    ");
        {
            Object var_testvariable18 = renderContext.getObjectModel().resolveProperty(_global_model, "subtitle");
            if (renderContext.getObjectModel().toBoolean(var_testvariable18)) {
                out.write("<p class=\"cmp-contenttile__subtitle\">");
                {
                    String var_19 = (("\n        " + renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(_global_model, "subtitle"), "text"))) + "\n    ");
                    out.write(renderContext.getObjectModel().toString(var_19));
                }
                out.write("</p>");
            }
        }
        out.write("\n</div>");
    }
}
out.write("\n");


// End Of Main Template Body ----------------------------------------------------------------------
    }



    {
//Sub-Templates Initialization --------------------------------------------------------------------



//End of Sub-Templates Initialization -------------------------------------------------------------
    }

}

