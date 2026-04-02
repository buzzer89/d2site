package com.d2.core.servlets;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

import com.d2.core.services.SitemapService;
import com.d2.core.services.impl.SitemapServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import com.d2.core.testcontext.AppAemContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(AemContextExtension.class)
class SitemapServletTest {

    private final AemContext context = AppAemContext.newAemContext();

    private SitemapServlet servlet;

    @BeforeEach
    void setup() {
        // Register the SitemapService implementation
        SitemapServiceImpl sitemapService = new SitemapServiceImpl();
        Map<String, Object> props = new HashMap<>();
        props.put("externalDomain", "https://www.example.com");
        props.put("includeUnpublished", true);
        context.registerInjectActivateService(sitemapService, props);

        servlet = context.registerInjectActivateService(new SitemapServlet());

        // Create test pages
        context.create().page("/content/d2site", "/conf/d2site/settings/wcm/templates/page-content",
                "jcr:title", "D2 Site");
        context.create().page("/content/d2site/us", "/conf/d2site/settings/wcm/templates/page-content",
                "jcr:title", "US");
        context.create().page("/content/d2site/us/en", "/conf/d2site/settings/wcm/templates/page-content",
                "jcr:title", "English");
    }

    @Test
    void testDoGetReturnsSitemapXml() throws ServletException, IOException {
        context.currentResource("/content/d2site/us/en/jcr:content");

        servlet.doGet(context.request(), context.response());

        assertTrue(context.response().getContentType().startsWith("application/xml"));
        String output = context.response().getOutputAsString();
        assertTrue(output.contains("<urlset"));
        assertTrue(output.contains("<loc>"));
        assertTrue(output.contains(".html</loc>"));
    }

    @Test
    void testSitemapFromContentRoot() throws ServletException, IOException {
        context.currentResource("/content/d2site/jcr:content");

        servlet.doGet(context.request(), context.response());

        String output = context.response().getOutputAsString();
        assertTrue(output.contains("https://www.example.com/content/d2site.html"));
    }
}
