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
class TestimonialCarouselModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    private Page page;

    @BeforeEach
    void setup() {
        page = context.create().page("/content/mypage");
    }

    @Test
    void testEmptyItems() {
        Resource resource = context.create().resource(page, "carousel-empty",
            "sling:resourceType", "d2site/components/testimonialcarousel");

        TestimonialCarouselModel model = resource.adaptTo(TestimonialCarouselModel.class);

        assertNotNull(model);
        assertNotNull(model.getItems());
        assertTrue(model.getItems().isEmpty());
    }

    @Test
    void testSingleTestimonialAllFields() {
        Resource resource = context.create().resource(page, "carousel-single",
            "sling:resourceType", "d2site/components/testimonialcarousel");
        context.create().resource(resource, "items/item0",
            "authorPhoto", "/content/dam/d2site/photo1.jpg",
            "quoteText", "This is a great product!",
            "authorName", "John Doe",
            "designation", "CEO, Acme Corp");

        TestimonialCarouselModel model = resource.adaptTo(TestimonialCarouselModel.class);

        assertNotNull(model);
        List<TestimonialCarouselModel.TestimonialItem> items = model.getItems();
        assertEquals(1, items.size());

        TestimonialCarouselModel.TestimonialItem item = items.get(0);
        assertEquals("/content/dam/d2site/photo1.jpg", item.getAuthorPhoto());
        assertEquals("This is a great product!", item.getQuoteText());
        assertEquals("John Doe", item.getAuthorName());
        assertEquals("CEO, Acme Corp", item.getDesignation());
    }

    @Test
    void testMultipleTestimonials() {
        Resource resource = context.create().resource(page, "carousel-multi",
            "sling:resourceType", "d2site/components/testimonialcarousel");
        context.create().resource(resource, "items/item0",
            "authorPhoto", "/content/dam/d2site/photo1.jpg",
            "quoteText", "First quote",
            "authorName", "Author One",
            "designation", "Designer");
        context.create().resource(resource, "items/item1",
            "authorPhoto", "/content/dam/d2site/photo2.jpg",
            "quoteText", "Second quote",
            "authorName", "Author Two",
            "designation", "Developer");

        TestimonialCarouselModel model = resource.adaptTo(TestimonialCarouselModel.class);

        assertNotNull(model);
        List<TestimonialCarouselModel.TestimonialItem> items = model.getItems();
        assertEquals(2, items.size());
        assertEquals("First quote", items.get(0).getQuoteText());
        assertEquals("Second quote", items.get(1).getQuoteText());
    }

    @Test
    void testPartialFieldsMissingPhoto() {
        Resource resource = context.create().resource(page, "carousel-no-photo",
            "sling:resourceType", "d2site/components/testimonialcarousel");
        context.create().resource(resource, "items/item0",
            "quoteText", "A testimonial without photo",
            "authorName", "Jane Smith",
            "designation", "Manager");

        TestimonialCarouselModel model = resource.adaptTo(TestimonialCarouselModel.class);

        assertNotNull(model);
        List<TestimonialCarouselModel.TestimonialItem> items = model.getItems();
        assertEquals(1, items.size());

        TestimonialCarouselModel.TestimonialItem item = items.get(0);
        assertNull(item.getAuthorPhoto());
        assertEquals("A testimonial without photo", item.getQuoteText());
        assertEquals("Jane Smith", item.getAuthorName());
        assertEquals("Manager", item.getDesignation());
    }

    @Test
    void testPartialFieldsMissingDesignation() {
        Resource resource = context.create().resource(page, "carousel-no-designation",
            "sling:resourceType", "d2site/components/testimonialcarousel");
        context.create().resource(resource, "items/item0",
            "authorPhoto", "/content/dam/d2site/photo1.jpg",
            "quoteText", "A testimonial without designation",
            "authorName", "Bob Brown");

        TestimonialCarouselModel model = resource.adaptTo(TestimonialCarouselModel.class);

        assertNotNull(model);
        List<TestimonialCarouselModel.TestimonialItem> items = model.getItems();
        assertEquals(1, items.size());

        TestimonialCarouselModel.TestimonialItem item = items.get(0);
        assertEquals("/content/dam/d2site/photo1.jpg", item.getAuthorPhoto());
        assertEquals("A testimonial without designation", item.getQuoteText());
        assertEquals("Bob Brown", item.getAuthorName());
        assertNull(item.getDesignation());
    }

    @Test
    void testAllFieldsNull() {
        Resource resource = context.create().resource(page, "carousel-null-fields",
            "sling:resourceType", "d2site/components/testimonialcarousel");
        context.create().resource(resource, "items/item0");

        TestimonialCarouselModel model = resource.adaptTo(TestimonialCarouselModel.class);

        assertNotNull(model);
        List<TestimonialCarouselModel.TestimonialItem> items = model.getItems();
        assertEquals(1, items.size());

        TestimonialCarouselModel.TestimonialItem item = items.get(0);
        assertNull(item.getAuthorPhoto());
        assertNull(item.getQuoteText());
        assertNull(item.getAuthorName());
        assertNull(item.getDesignation());
    }
}
