package com.d2.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = Resource.class)
public class FaqAccordionModel {

    @SlingObject(injectionStrategy = InjectionStrategy.OPTIONAL)
    private Resource resource;

    private List<FaqItem> items;

    @PostConstruct
    protected void init() {
        items = new ArrayList<>();
        if (resource != null) {
            Resource faqItemsResource = resource.getChild("faqItems");
            if (faqItemsResource != null) {
                for (Resource child : faqItemsResource.getChildren()) {
                    ValueMap props = child.getValueMap();
                    String question = props.get("question", String.class);
                    String answer = props.get("answer", String.class);
                    if (question != null && !question.trim().isEmpty()) {
                        items.add(new FaqItem(question, answer != null ? answer : ""));
                    }
                }
            }
        }
    }

    public List<FaqItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public static class FaqItem {
        private final String question;
        private final String answer;

        public FaqItem(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }
    }
}
