package com.d2.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class ColumnControlModel {

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "2")
    private String layout;

    public int getColumnCount() {
        try {
            int count = Integer.parseInt(layout);
            return Math.min(Math.max(count, 1), 4);
        } catch (NumberFormatException e) {
            return 2;
        }
    }

    public List<String> getColumnNames() {
        int count = getColumnCount();
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            names.add("column-" + i);
        }
        return Collections.unmodifiableList(names);
    }

    public String getCssClass() {
        return "cmp-columncontrol cmp-columncontrol--" + getColumnCount() + "col";
    }

    public String getLayout() {
        return layout;
    }
}
