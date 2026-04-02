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
class CardListModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    private Page page;

    @BeforeEach
    void setup() {
        page = context.create().page("/content/mypage");
    }

    @Test
    void testEmptyItems() {
        Resource resource = context.create().resource(page, "cardlist-empty",
            "sling:resourceType", "d2site/components/cardlist");

        CardListModel model = resource.adaptTo(CardListModel.class);

        assertNotNull(model);
        assertNotNull(model.getItems());
        assertTrue(model.getItems().isEmpty());
    }

    @Test
    void testSingleCardAllFields() {
        Resource resource = context.create().resource(page, "cardlist-single",
            "sling:resourceType", "d2site/components/cardlist",
            "layout", "3");

        context.create().resource(resource, "items/item0",
            "imagePath", "/content/dam/d2site/card1.jpg",
            "title", "Card Title",
            "description", "Card description text",
            "ctaLabel", "Learn More",
            "ctaLink", "/content/d2site/us/en/details");

        CardListModel model = resource.adaptTo(CardListModel.class);

        assertNotNull(model);
        List<CardListModel.CardItem> items = model.getItems();
        assertEquals(1, items.size());

        CardListModel.CardItem card = items.get(0);
        assertEquals("/content/dam/d2site/card1.jpg", card.getImagePath());
        assertEquals("Card Title", card.getTitle());
        assertEquals("Card description text", card.getDescription());
        assertEquals("Learn More", card.getCtaLabel());
        assertEquals("/content/d2site/us/en/details", card.getCtaLink());
    }

    @Test
    void testMultipleCards() {
        Resource resource = context.create().resource(page, "cardlist-multi",
            "sling:resourceType", "d2site/components/cardlist",
            "layout", "3");

        context.create().resource(resource, "items/item0",
            "title", "Card One");
        context.create().resource(resource, "items/item1",
            "title", "Card Two");
        context.create().resource(resource, "items/item2",
            "title", "Card Three");

        CardListModel model = resource.adaptTo(CardListModel.class);

        assertNotNull(model);
        List<CardListModel.CardItem> items = model.getItems();
        assertEquals(3, items.size());
        assertEquals("Card One", items.get(0).getTitle());
        assertEquals("Card Two", items.get(1).getTitle());
        assertEquals("Card Three", items.get(2).getTitle());
    }

    @Test
    void testPartialFields() {
        Resource resource = context.create().resource(page, "cardlist-partial",
            "sling:resourceType", "d2site/components/cardlist");

        context.create().resource(resource, "items/item0",
            "title", "Only Title");

        CardListModel model = resource.adaptTo(CardListModel.class);

        assertNotNull(model);
        List<CardListModel.CardItem> items = model.getItems();
        assertEquals(1, items.size());

        CardListModel.CardItem card = items.get(0);
        assertEquals("Only Title", card.getTitle());
        assertNull(card.getImagePath());
        assertNull(card.getDescription());
        assertNull(card.getCtaLabel());
        assertNull(card.getCtaLink());
    }

    @Test
    void testDefaultLayout() {
        Resource resource = context.create().resource(page, "cardlist-default-layout",
            "sling:resourceType", "d2site/components/cardlist");

        CardListModel model = resource.adaptTo(CardListModel.class);

        assertNotNull(model);
        assertEquals(3, model.getColumnCount());
        assertEquals("cmp-cardlist cmp-cardlist--3col", model.getCssClass());
    }

    @Test
    void testLayout2Columns() {
        Resource resource = context.create().resource(page, "cardlist-2col",
            "sling:resourceType", "d2site/components/cardlist",
            "layout", "2");

        CardListModel model = resource.adaptTo(CardListModel.class);

        assertNotNull(model);
        assertEquals(2, model.getColumnCount());
        assertEquals("cmp-cardlist cmp-cardlist--2col", model.getCssClass());
    }

    @Test
    void testLayout3Columns() {
        Resource resource = context.create().resource(page, "cardlist-3col",
            "sling:resourceType", "d2site/components/cardlist",
            "layout", "3");

        CardListModel model = resource.adaptTo(CardListModel.class);

        assertNotNull(model);
        assertEquals(3, model.getColumnCount());
        assertEquals("cmp-cardlist cmp-cardlist--3col", model.getCssClass());
    }

    @Test
    void testLayout4Columns() {
        Resource resource = context.create().resource(page, "cardlist-4col",
            "sling:resourceType", "d2site/components/cardlist",
            "layout", "4");

        CardListModel model = resource.adaptTo(CardListModel.class);

        assertNotNull(model);
        assertEquals(4, model.getColumnCount());
        assertEquals("cmp-cardlist cmp-cardlist--4col", model.getCssClass());
    }

    @Test
    void testInvalidLayout() {
        Resource resource = context.create().resource(page, "cardlist-invalid",
            "sling:resourceType", "d2site/components/cardlist",
            "layout", "abc");

        CardListModel model = resource.adaptTo(CardListModel.class);

        assertNotNull(model);
        assertEquals(3, model.getColumnCount());
        assertEquals("cmp-cardlist cmp-cardlist--3col", model.getCssClass());
    }

    @Test
    void testClampedMinLayout() {
        Resource resource = context.create().resource(page, "cardlist-min",
            "sling:resourceType", "d2site/components/cardlist",
            "layout", "0");

        CardListModel model = resource.adaptTo(CardListModel.class);

        assertNotNull(model);
        assertEquals(2, model.getColumnCount());
    }

    @Test
    void testClampedMaxLayout() {
        Resource resource = context.create().resource(page, "cardlist-max",
            "sling:resourceType", "d2site/components/cardlist",
            "layout", "10");

        CardListModel model = resource.adaptTo(CardListModel.class);

        assertNotNull(model);
        assertEquals(4, model.getColumnCount());
    }
}
