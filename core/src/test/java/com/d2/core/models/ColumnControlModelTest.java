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
class ColumnControlModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    private Page page;

    @BeforeEach
    void setup() {
        page = context.create().page("/content/mypage");
    }

    @Test
    void testOneColumn() {
        Resource resource = context.create().resource(page, "colctrl-1",
            "sling:resourceType", "d2site/components/columncontrol",
            "layout", "1");

        ColumnControlModel model = resource.adaptTo(ColumnControlModel.class);

        assertNotNull(model);
        assertEquals(1, model.getColumnCount());
        assertEquals("1", model.getLayout());
        assertEquals("cmp-columncontrol cmp-columncontrol--1col", model.getCssClass());

        List<String> names = model.getColumnNames();
        assertEquals(1, names.size());
        assertEquals("column-1", names.get(0));
    }

    @Test
    void testTwoColumns() {
        Resource resource = context.create().resource(page, "colctrl-2",
            "sling:resourceType", "d2site/components/columncontrol",
            "layout", "2");

        ColumnControlModel model = resource.adaptTo(ColumnControlModel.class);

        assertNotNull(model);
        assertEquals(2, model.getColumnCount());
        assertEquals("cmp-columncontrol cmp-columncontrol--2col", model.getCssClass());

        List<String> names = model.getColumnNames();
        assertEquals(2, names.size());
        assertEquals("column-1", names.get(0));
        assertEquals("column-2", names.get(1));
    }

    @Test
    void testThreeColumns() {
        Resource resource = context.create().resource(page, "colctrl-3",
            "sling:resourceType", "d2site/components/columncontrol",
            "layout", "3");

        ColumnControlModel model = resource.adaptTo(ColumnControlModel.class);

        assertNotNull(model);
        assertEquals(3, model.getColumnCount());
        assertEquals("cmp-columncontrol cmp-columncontrol--3col", model.getCssClass());

        List<String> names = model.getColumnNames();
        assertEquals(3, names.size());
        assertEquals("column-3", names.get(2));
    }

    @Test
    void testFourColumns() {
        Resource resource = context.create().resource(page, "colctrl-4",
            "sling:resourceType", "d2site/components/columncontrol",
            "layout", "4");

        ColumnControlModel model = resource.adaptTo(ColumnControlModel.class);

        assertNotNull(model);
        assertEquals(4, model.getColumnCount());
        assertEquals("cmp-columncontrol cmp-columncontrol--4col", model.getCssClass());

        List<String> names = model.getColumnNames();
        assertEquals(4, names.size());
        assertEquals("column-4", names.get(3));
    }

    @Test
    void testDefaultLayout() {
        Resource resource = context.create().resource(page, "colctrl-default",
            "sling:resourceType", "d2site/components/columncontrol");

        ColumnControlModel model = resource.adaptTo(ColumnControlModel.class);

        assertNotNull(model);
        assertEquals(2, model.getColumnCount());
        assertEquals("cmp-columncontrol cmp-columncontrol--2col", model.getCssClass());

        List<String> names = model.getColumnNames();
        assertEquals(2, names.size());
    }

    @Test
    void testInvalidLayout() {
        Resource resource = context.create().resource(page, "colctrl-invalid",
            "sling:resourceType", "d2site/components/columncontrol",
            "layout", "abc");

        ColumnControlModel model = resource.adaptTo(ColumnControlModel.class);

        assertNotNull(model);
        assertEquals(2, model.getColumnCount());
    }

    @Test
    void testLayoutClampedToMax() {
        Resource resource = context.create().resource(page, "colctrl-over",
            "sling:resourceType", "d2site/components/columncontrol",
            "layout", "10");

        ColumnControlModel model = resource.adaptTo(ColumnControlModel.class);

        assertNotNull(model);
        assertEquals(4, model.getColumnCount());
        assertEquals(4, model.getColumnNames().size());
    }

    @Test
    void testLayoutClampedToMin() {
        Resource resource = context.create().resource(page, "colctrl-under",
            "sling:resourceType", "d2site/components/columncontrol",
            "layout", "0");

        ColumnControlModel model = resource.adaptTo(ColumnControlModel.class);

        assertNotNull(model);
        assertEquals(1, model.getColumnCount());
        assertEquals(1, model.getColumnNames().size());
    }

    @Test
    void testDefaultPadding() {
        Resource resource = context.create().resource(page, "colctrl-pad-default",
            "sling:resourceType", "d2site/components/columncontrol",
            "layout", "2");

        ColumnControlModel model = resource.adaptTo(ColumnControlModel.class);

        assertNotNull(model);
        assertEquals("none", model.getColumnPadding());
        assertFalse(model.getCssClass().contains("padding"));
    }

    @Test
    void testDefaultGap() {
        Resource resource = context.create().resource(page, "colctrl-gap-default",
            "sling:resourceType", "d2site/components/columncontrol",
            "layout", "2");

        ColumnControlModel model = resource.adaptTo(ColumnControlModel.class);

        assertNotNull(model);
        assertEquals("none", model.getColumnGap());
        assertFalse(model.getCssClass().contains("gap"));
    }

    @Test
    void testPaddingSmall() {
        Resource resource = context.create().resource(page, "colctrl-pad-small",
            "sling:resourceType", "d2site/components/columncontrol",
            "layout", "2",
            "columnPadding", "small");

        ColumnControlModel model = resource.adaptTo(ColumnControlModel.class);

        assertNotNull(model);
        assertEquals("small", model.getColumnPadding());
        assertTrue(model.getCssClass().contains("cmp-columncontrol--padding-small"));
    }

    @Test
    void testGapMedium() {
        Resource resource = context.create().resource(page, "colctrl-gap-medium",
            "sling:resourceType", "d2site/components/columncontrol",
            "layout", "3",
            "columnGap", "medium");

        ColumnControlModel model = resource.adaptTo(ColumnControlModel.class);

        assertNotNull(model);
        assertEquals("medium", model.getColumnGap());
        assertTrue(model.getCssClass().contains("cmp-columncontrol--gap-medium"));
    }

    @Test
    void testCombinedPaddingAndGap() {
        Resource resource = context.create().resource(page, "colctrl-pad-gap",
            "sling:resourceType", "d2site/components/columncontrol",
            "layout", "4",
            "columnPadding", "large",
            "columnGap", "small");

        ColumnControlModel model = resource.adaptTo(ColumnControlModel.class);

        assertNotNull(model);
        assertEquals("large", model.getColumnPadding());
        assertEquals("small", model.getColumnGap());
        String css = model.getCssClass();
        assertTrue(css.contains("cmp-columncontrol--padding-large"));
        assertTrue(css.contains("cmp-columncontrol--gap-small"));
    }

    @Test
    void testBackwardCompatibility() {
        Resource resource = context.create().resource(page, "colctrl-compat",
            "sling:resourceType", "d2site/components/columncontrol",
            "layout", "2");

        ColumnControlModel model = resource.adaptTo(ColumnControlModel.class);

        assertNotNull(model);
        assertEquals("cmp-columncontrol cmp-columncontrol--2col", model.getCssClass());
    }
}
