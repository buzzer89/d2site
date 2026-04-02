package com.d2.core.models;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class CardListModel {

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String layout;

    @ChildResource(injectionStrategy = InjectionStrategy.OPTIONAL)
    private List<Resource> items;

    public int getColumnCount() {
        try {
            int count = Integer.parseInt(layout);
            return Math.min(Math.max(count, 2), 4);
        } catch (NumberFormatException | NullPointerException e) {
            return 3;
        }
    }

    public String getCssClass() {
        return "cmp-cardlist cmp-cardlist--" + getColumnCount() + "col";
    }

    public List<CardItem> getItems() {
        if (items == null) {
            return Collections.emptyList();
        }
        return items.stream()
                .map(r -> r.adaptTo(CardItem.class))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Model(adaptables = Resource.class)
    public static class CardItem {

        @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
        private String imagePath;

        @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
        private String title;

        @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
        private String description;

        @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
        private String ctaLabel;

        @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
        private String ctaLink;

        public String getImagePath() {
            return imagePath;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getCtaLabel() {
            return ctaLabel;
        }

        public String getCtaLink() {
            return ctaLink;
        }
    }
}
