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
    void testEmptyComponent() {
        Resource resource = context.create().resource(page, "faq-empty",
            "sling:resourceType", "d2site/components/faqaccordion");

        FaqAccordionModel model = resource.adaptTo(FaqAccordionModel.class);

        assertNotNull(model);
        assertTrue(model.isEmpty());
        assertEquals(0, model.getItems().size());
    }

    @Test
    void testEmptyMultifieldNode() {
        Resource resource = context.create().resource(page, "faq-emptynode",
            "sling:resourceType", "d2site/components/faqaccordion");
        context.create().resource(resource, "faqItems");

        FaqAccordionModel model = resource.adaptTo(FaqAccordionModel.class);

        assertNotNull(model);
        assertTrue(model.isEmpty());
        assertEquals(0, model.getItems().size());
    }

    @Test
    void testSingleItem() {
        Resource resource = context.create().resource(page, "faq-single",
            "sling:resourceType", "d2site/components/faqaccordion");
        Resource faqItems = context.create().resource(resource, "faqItems");
        context.create().resource(faqItems, "item0",
            "question", "What is AEM?",
            "answer", "<p>Adobe Experience Manager is a CMS.</p>");

        FaqAccordionModel model = resource.adaptTo(FaqAccordionModel.class);

        assertNotNull(model);
        assertFalse(model.isEmpty());

        List<FaqAccordionModel.FaqItem> items = model.getItems();
        assertEquals(1, items.size());
        assertEquals("What is AEM?", items.get(0).getQuestion());
        assertEquals("<p>Adobe Experience Manager is a CMS.</p>", items.get(0).getAnswer());
    }

    @Test
    void testMultipleItems() {
        Resource resource = context.create().resource(page, "faq-multi",
            "sling:resourceType", "d2site/components/faqaccordion");
        Resource faqItems = context.create().resource(resource, "faqItems");
        context.create().resource(faqItems, "item0",
            "question", "Question One",
            "answer", "<p>Answer one.</p>");
        context.create().resource(faqItems, "item1",
            "question", "Question Two",
            "answer", "<p>Answer two.</p>");
        context.create().resource(faqItems, "item2",
            "question", "Question Three",
            "answer", "<p>Answer three.</p>");

        FaqAccordionModel model = resource.adaptTo(FaqAccordionModel.class);

        assertNotNull(model);
        assertFalse(model.isEmpty());

        List<FaqAccordionModel.FaqItem> items = model.getItems();
        assertEquals(3, items.size());
        assertEquals("Question One", items.get(0).getQuestion());
        assertEquals("Question Two", items.get(1).getQuestion());
        assertEquals("Question Three", items.get(2).getQuestion());
    }

    @Test
    void testItemWithMissingQuestion() {
        Resource resource = context.create().resource(page, "faq-noquestion",
            "sling:resourceType", "d2site/components/faqaccordion");
        Resource faqItems = context.create().resource(resource, "faqItems");
        context.create().resource(faqItems, "item0",
            "answer", "<p>Answer without question.</p>");
        context.create().resource(faqItems, "item1",
            "question", "Valid Question",
            "answer", "<p>Valid answer.</p>");

        FaqAccordionModel model = resource.adaptTo(FaqAccordionModel.class);

        assertNotNull(model);
        assertFalse(model.isEmpty());

        List<FaqAccordionModel.FaqItem> items = model.getItems();
        assertEquals(1, items.size());
        assertEquals("Valid Question", items.get(0).getQuestion());
    }

    @Test
    void testItemWithMissingAnswer() {
        Resource resource = context.create().resource(page, "faq-noanswer",
            "sling:resourceType", "d2site/components/faqaccordion");
        Resource faqItems = context.create().resource(resource, "faqItems");
        context.create().resource(faqItems, "item0",
            "question", "Question without answer");

        FaqAccordionModel model = resource.adaptTo(FaqAccordionModel.class);

        assertNotNull(model);
        assertFalse(model.isEmpty());

        List<FaqAccordionModel.FaqItem> items = model.getItems();
        assertEquals(1, items.size());
        assertEquals("Question without answer", items.get(0).getQuestion());
        assertEquals("", items.get(0).getAnswer());
    }

    @Test
    void testItemsListIsUnmodifiable() {
        Resource resource = context.create().resource(page, "faq-unmod",
            "sling:resourceType", "d2site/components/faqaccordion");
        Resource faqItems = context.create().resource(resource, "faqItems");
        context.create().resource(faqItems, "item0",
            "question", "Q1",
            "answer", "A1");

        FaqAccordionModel model = resource.adaptTo(FaqAccordionModel.class);
        List<FaqAccordionModel.FaqItem> items = model.getItems();

        assertThrows(UnsupportedOperationException.class, () -> items.add(new FaqAccordionModel.FaqItem("x", "y")));
    }
}
