package com.d2.core.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import com.d2.core.testcontext.AppAemContext;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(AemContextExtension.class)
class SitemapServiceImplTest {

    private final AemContext context = AppAemContext.newAemContext();

    private SitemapServiceImpl service;

    @BeforeEach
    void setup() {
        service = new SitemapServiceImpl();

        // Activate with default config (includeUnpublished = true for test visibility)
        Map<String, Object> props = new HashMap<>();
        props.put("externalDomain", "https://www.example.com");
        props.put("includeUnpublished", true);
        context.registerInjectActivateService(service, props);
    }

    @Test
    void testGenerateSitemapWithPages() {
        Page rootPage = context.create().page("/content/d2site", "/conf/d2site/settings/wcm/templates/page-content",
                "jcr:title", "D2 Site");
        context.create().page("/content/d2site/us", "/conf/d2site/settings/wcm/templates/page-content",
                "jcr:title", "US");
        context.create().page("/content/d2site/us/en", "/conf/d2site/settings/wcm/templates/page-content",
                "jcr:title", "English");

        String sitemap = service.generateSitemap(context.resourceResolver(), "/content/d2site");

        assertNotNull(sitemap);
        assertTrue(sitemap.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
        assertTrue(sitemap.contains("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">"));
        assertTrue(sitemap.contains("<loc>https://www.example.com/content/d2site.html</loc>"));
        assertTrue(sitemap.contains("<loc>https://www.example.com/content/d2site/us.html</loc>"));
        assertTrue(sitemap.contains("<loc>https://www.example.com/content/d2site/us/en.html</loc>"));
        assertTrue(sitemap.contains("</urlset>"));
    }

    @Test
    void testExcludesHiddenPages() {
        context.create().page("/content/d2site", "/conf/d2site/settings/wcm/templates/page-content",
                "jcr:title", "D2 Site");
        context.create().page("/content/d2site/hidden", "/conf/d2site/settings/wcm/templates/page-content",
                "jcr:title", "Hidden Page", "hideInNav", true);

        String sitemap = service.generateSitemap(context.resourceResolver(), "/content/d2site");

        assertTrue(sitemap.contains("/content/d2site.html"));
        assertFalse(sitemap.contains("/content/d2site/hidden.html"));
    }

    @Test
    void testPublishedOnlyMode() {
        // Re-activate with includeUnpublished = false
        SitemapServiceImpl strictService = new SitemapServiceImpl();
        Map<String, Object> props = new HashMap<>();
        props.put("externalDomain", "https://www.example.com");
        props.put("includeUnpublished", false);
        context.registerInjectActivateService(strictService, props);

        context.create().page("/content/d2site", "/conf/d2site/settings/wcm/templates/page-content",
                "jcr:title", "D2 Site");
        Page publishedPage = context.create().page("/content/d2site/published",
                "/conf/d2site/settings/wcm/templates/page-content",
                "jcr:title", "Published");

        // Set the replication action on the published page
        Resource jcrContent = publishedPage.getContentResource();
        if (jcrContent != null) {
            ModifiableValueMap mvm = jcrContent.adaptTo(ModifiableValueMap.class);
            if (mvm != null) {
                mvm.put("cq:lastReplicationAction", "Activate");
            }
        }

        context.create().page("/content/d2site/draft",
                "/conf/d2site/settings/wcm/templates/page-content",
                "jcr:title", "Draft");

        String sitemap = strictService.generateSitemap(context.resourceResolver(), "/content/d2site");

        assertFalse(sitemap.contains("/content/d2site.html")); // root not activated
        assertTrue(sitemap.contains("/content/d2site/published.html"));
        assertFalse(sitemap.contains("/content/d2site/draft.html"));
    }

    @Test
    void testNonExistentRoot() {
        String sitemap = service.generateSitemap(context.resourceResolver(), "/content/nonexistent");

        assertNotNull(sitemap);
        assertTrue(sitemap.contains("<urlset"));
        assertFalse(sitemap.contains("<url>"));
    }

    @Test
    void testEmptySite() {
        context.create().page("/content/emptysite", "/conf/d2site/settings/wcm/templates/page-content",
                "jcr:title", "Empty Site");

        String sitemap = service.generateSitemap(context.resourceResolver(), "/content/emptysite");

        assertNotNull(sitemap);
        assertTrue(sitemap.contains("<loc>https://www.example.com/content/emptysite.html</loc>"));
        // Only one <url> entry for the root page
        assertEquals(1, countOccurrences(sitemap, "<url>"));
    }

    private int countOccurrences(String str, String sub) {
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }
}
