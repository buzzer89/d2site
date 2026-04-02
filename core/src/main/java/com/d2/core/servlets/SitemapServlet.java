package com.d2.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.d2.core.services.SitemapService;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

@Component(service = { Servlet.class })
@SlingServletResourceTypes(
        resourceTypes = "d2site/components/page",
        methods = HttpConstants.METHOD_GET,
        extensions = "xml",
        selectors = "sitemap")
@ServiceDescription("Sitemap XML Servlet")
public class SitemapServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private SitemapService sitemapService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {

        PageManager pageManager = req.getResourceResolver().adaptTo(PageManager.class);
        if (pageManager == null) {
            resp.sendError(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Could not obtain PageManager");
            return;
        }

        Page currentPage = pageManager.getContainingPage(req.getResource());
        if (currentPage == null) {
            resp.sendError(SlingHttpServletResponse.SC_NOT_FOUND, "No page found for this resource");
            return;
        }

        // Walk up to the content root (the page directly under /content)
        Page rootPage = currentPage;
        while (rootPage.getParent() != null && rootPage.getDepth() > 2) {
            rootPage = rootPage.getParent();
        }

        String sitemapXml = sitemapService.generateSitemap(req.getResourceResolver(), rootPage.getPath());

        resp.setContentType("application/xml");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(sitemapXml);
    }
}
