package com.d2.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class ContentTileModel {

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String iconPath;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String headline;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String subtitle;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String backgroundColor;

    public String getCssClass() {
        String bgColor = backgroundColor != null ? backgroundColor : "white";
        return "cmp-contenttile cmp-contenttile--bg-" + bgColor;
    }

    public String getIconPath() {
        return iconPath;
    }

    public String getHeadline() {
        return headline;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }
}
