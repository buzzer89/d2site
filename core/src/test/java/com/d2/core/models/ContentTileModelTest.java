package com.d2.core.models;

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
class ContentTileModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    private Page page;

    @BeforeEach
    void setup() {
        page = context.create().page("/content/mypage");
    }

    @Test
    void testAllFieldsPopulated() {
        Resource resource = context.create().resource(page, "contenttile-full",
            "sling:resourceType", "d2site/components/contenttile",
            "iconPath", "/content/dam/d2site/icons/star.svg",
            "headline", "Test Headline",
            "subtitle", "Test Subtitle",
            "backgroundColor", "blue");

        ContentTileModel model = resource.adaptTo(ContentTileModel.class);

        assertNotNull(model);
        assertEquals("/content/dam/d2site/icons/star.svg", model.getIconPath());
        assertEquals("Test Headline", model.getHeadline());
        assertEquals("Test Subtitle", model.getSubtitle());
        assertEquals("blue", model.getBackgroundColor());
        assertEquals("cmp-contenttile cmp-contenttile--bg-blue", model.getCssClass());
    }

    @Test
    void testEmptyResource() {
        Resource resource = context.create().resource(page, "contenttile-empty",
            "sling:resourceType", "d2site/components/contenttile");

        ContentTileModel model = resource.adaptTo(ContentTileModel.class);

        assertNotNull(model);
        assertNull(model.getIconPath());
        assertNull(model.getHeadline());
        assertNull(model.getSubtitle());
        assertNull(model.getBackgroundColor());
        assertEquals("cmp-contenttile cmp-contenttile--bg-white", model.getCssClass());
    }

    @Test
    void testPartialFields() {
        Resource resource = context.create().resource(page, "contenttile-partial",
            "sling:resourceType", "d2site/components/contenttile",
            "headline", "Only Headline",
            "backgroundColor", "dark-blue");

        ContentTileModel model = resource.adaptTo(ContentTileModel.class);

        assertNotNull(model);
        assertNull(model.getIconPath());
        assertEquals("Only Headline", model.getHeadline());
        assertNull(model.getSubtitle());
        assertEquals("dark-blue", model.getBackgroundColor());
        assertEquals("cmp-contenttile cmp-contenttile--bg-dark-blue", model.getCssClass());
    }
}
