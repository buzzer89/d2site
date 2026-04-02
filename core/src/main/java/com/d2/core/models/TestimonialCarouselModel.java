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
public class TestimonialCarouselModel {

    @ChildResource(injectionStrategy = InjectionStrategy.OPTIONAL)
    private List<Resource> items;

    public List<TestimonialItem> getItems() {
        if (items == null) {
            return Collections.emptyList();
        }
        return items.stream()
                .map(r -> r.adaptTo(TestimonialItem.class))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Model(adaptables = Resource.class)
    public static class TestimonialItem {

        @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
        private String authorPhoto;

        @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
        private String quoteText;

        @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
        private String authorName;

        @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
        private String designation;

        public String getAuthorPhoto() {
            return authorPhoto;
        }

        public String getQuoteText() {
            return quoteText;
        }

        public String getAuthorName() {
            return authorName;
        }

        public String getDesignation() {
            return designation;
        }
    }
}
