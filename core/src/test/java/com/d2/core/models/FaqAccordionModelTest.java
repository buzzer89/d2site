package com.d2.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import com.d2.core.testcontext.AppAemContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class FaqAccordionModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    private Page page;

    @BeforeEach
    void setup() {
        page = context.create().page("/content/mypage");
    }

    @Test
    void testEmptyItems() {
        Resource resource = context.create().resource(page, "faq-empty",
            "sling:resourceType", "d2site/components/faqaccordion");

        FaqAccordionModel model = resource.adaptTo(FaqAccordionModel.class);

        assertNotNull(model);
        assertNotNull(model.getItems());
        assertTrue(model.getItems().isEmpty());
    }

    @Test
    void testSingleQAPair() {
        Resource resource = context.create().resource(page, "faq-single",
            "sling:resourceType", "d2site/components/faqaccordion");

        context.create().resource(resource, "items/item0",
            "question", "What is AEM?",
            "answer", "<p>AEM is Adobe Experience Manager.</p>");

        FaqAccordionModel model = resource.adaptTo(FaqAccordionModel.class);

        assertNotNull(model);
        List<FaqAccordionModel.FaqItem> items = model.getItems();
        assertEquals(1, items.size());
        assertEquals("What is AEM?", items.get(0).getQuestion());
        assertEquals("<p>AEM is Adobe Experience Manager.</p>", items.get(0).getAnswer());
    }

    @Test
    void testMultipleQAPairs() {
        Resource resource = context.create().resource(page, "faq-multi",
            "sling:resourceType", "d2site/components/faqaccordion");

        context.create().resource(resource, "items/item0",
            "question", "Question 1",
            "answer", "<p>Answer 1</p>");
        context.create().resource(resource, "items/item1",
            "question", "Question 2",
            "answer", "<p>Answer 2</p>");
        context.create().resource(resource, "items/item2",
            "question", "Question 3",
            "answer", "<p>Answer 3</p>");

        FaqAccordionModel model = resource.adaptTo(FaqAccordionModel.class);

        assertNotNull(model);
        List<FaqAccordionModel.FaqItem> items = model.getItems();
        assertEquals(3, items.size());
        assertEquals("Question 1", items.get(0).getQuestion());
        assertEquals("Question 2", items.get(1).getQuestion());
        assertEquals("Question 3", items.get(2).getQuestion());
        assertEquals("<p>Answer 3</p>", items.get(2).getAnswer());
    }

    @Test
    void testNullResourceHandling() {
        Resource resource = context.create().resource(page, "faq-null",
            "sling:resourceType", "d2site/components/faqaccordion");

        FaqAccordionModel model = resource.adaptTo(FaqAccordionModel.class);

        assertNotNull(model);
        List<FaqAccordionModel.FaqItem> items = model.getItems();
        assertNotNull(items);
        assertEquals(0, items.size());
    }

    @Test
    void testItemWithMissingFields() {
        Resource resource = context.create().resource(page, "faq-partial",
            "sling:resourceType", "d2site/components/faqaccordion");

        context.create().resource(resource, "items/item0",
            "question", "Only question, no answer");

        FaqAccordionModel model = resource.adaptTo(FaqAccordionModel.class);

        assertNotNull(model);
        List<FaqAccordionModel.FaqItem> items = model.getItems();
        assertEquals(1, items.size());
        assertEquals("Only question, no answer", items.get(0).getQuestion());
        assertNull(items.get(0).getAnswer());
    }
}
