package com.d2.core.models;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;

@Model(adaptables = Resource.class)
public class FaqAccordionModel {

    @ChildResource(injectionStrategy = InjectionStrategy.OPTIONAL)
    private List<Resource> items;

    public List<FaqItem> getItems() {
        if (items == null) {
            return Collections.emptyList();
        }
        return items.stream()
                .map(r -> r.adaptTo(FaqItem.class))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Model(adaptables = Resource.class)
    public static class FaqItem {

        @org.apache.sling.models.annotations.injectorspecific.ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
        private String question;

        @org.apache.sling.models.annotations.injectorspecific.ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
        private String answer;

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }
    }
}
