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
package org.apache.sling.scripting.sightly.apps.d2site.components.columncontrol;

import java.io.PrintWriter;
import java.util.Collection;
import javax.script.Bindings;

import org.apache.sling.scripting.sightly.render.RenderUnit;
import org.apache.sling.scripting.sightly.render.RenderContext;

public final class columncontrol__002e__html extends RenderUnit {

    @Override
    protected final void render(PrintWriter out,
                                Bindings bindings,
                                Bindings arguments,
                                RenderContext renderContext) {
// Main Template Body -----------------------------------------------------------------------------

Object _global_clientlib = null;
Object _global_model = null;
Collection var_collectionvar7_list_coerced$ = null;
_global_clientlib = renderContext.call("use", "/libs/granite/sightly/templates/clientlib.html", obj());
out.write("\n    ");
{
    Object var_templatevar0 = renderContext.getObjectModel().resolveProperty(_global_clientlib, "css");
    {
        String var_templateoptions1_field$_categories = "d2site.columncontrol";
        {
            java.util.Map var_templateoptions1 = obj().with("categories", var_templateoptions1_field$_categories);
            callUnit(out, renderContext, var_templatevar0, var_templateoptions1);
        }
    }
}
out.write("\n\n");
_global_model = renderContext.call("use", com.d2.core.models.ColumnControlModel.class.getName(), obj());
{
    Object var_testvariable2 = _global_model;
    if (renderContext.getObjectModel().toBoolean(var_testvariable2)) {
        out.write("<div data-cmp-is=\"columncontrol\"");
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
        out.write(">\n    ");
        {
            Object var_collectionvar7 = renderContext.getObjectModel().resolveProperty(_global_model, "columnNames");
            {
                long var_size8 = ((var_collectionvar7_list_coerced$ == null ? (var_collectionvar7_list_coerced$ = renderContext.getObjectModel().toCollection(var_collectionvar7)) : var_collectionvar7_list_coerced$).size());
                {
                    boolean var_notempty9 = (var_size8 > 0);
                    if (var_notempty9) {
                        {
                            long var_end12 = var_size8;
                            {
                                boolean var_validstartstepend13 = (((0 < var_size8) && true) && (var_end12 > 0));
                                if (var_validstartstepend13) {
                                    if (var_collectionvar7_list_coerced$ == null) {
                                        var_collectionvar7_list_coerced$ = renderContext.getObjectModel().toCollection(var_collectionvar7);
                                    }
                                    long var_index14 = 0;
                                    for (Object col : var_collectionvar7_list_coerced$) {
                                        {
                                            boolean var_traversal16 = (((var_index14 >= 0) && (var_index14 <= var_end12)) && true);
                                            if (var_traversal16) {
                                                out.write("<div class=\"cmp-columncontrol__column\">\n        ");
                                                {
                                                    Object var_resourcecontent17 = renderContext.call("includeResource", col, obj().with("resourceType", "wcm/foundation/components/responsivegrid"));
                                                    out.write(renderContext.getObjectModel().toString(var_resourcecontent17));
                                                }
                                                out.write("\n    </div>\n");
                                            }
                                        }
                                        var_index14++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            var_collectionvar7_list_coerced$ = null;
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

