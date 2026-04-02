package com.d2.core.services.impl;

import java.util.Iterator;

import com.d2.core.services.SitemapService;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Designate(ocd = SitemapServiceImpl.Config.class)
@Component(service = SitemapService.class, immediate = true)
public class SitemapServiceImpl implements SitemapService {

    private static final Logger LOG = LoggerFactory.getLogger(SitemapServiceImpl.class);

    private static final String SITEMAP_NS = "http://www.sitemaps.org/schemas/sitemap/0.9";
    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    private static final String REPLICATION_ACTION_PROPERTY = "cq:lastReplicationAction";
    private static final String ACTIVATE_ACTION = "Activate";

    @ObjectClassDefinition(name = "Sitemap Service Configuration",
            description = "Configuration for the Sitemap XML generator")
    public static @interface Config {

        @AttributeDefinition(name = "External Domain",
                description = "The externally accessible domain, e.g. https://www.example.com")
        String externalDomain() default "http://localhost:4503";

        @AttributeDefinition(name = "Include Unpublished Pages",
                description = "If true, includes all non-hidden pages regardless of replication status")
        boolean includeUnpublished() default false;
    }

    private String externalDomain;
    private boolean includeUnpublished;

    @Activate
    protected void activate(final Config config) {
        externalDomain = config.externalDomain();
        if (externalDomain.endsWith("/")) {
            externalDomain = externalDomain.substring(0, externalDomain.length() - 1);
        }
        includeUnpublished = config.includeUnpublished();
        LOG.debug("SitemapService activated with domain={}, includeUnpublished={}", externalDomain, includeUnpublished);
    }

    @Override
    public String generateSitemap(ResourceResolver resourceResolver, String rootPath) {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        if (pageManager == null) {
            LOG.error("Could not obtain PageManager");
            return XML_HEADER + "<urlset xmlns=\"" + SITEMAP_NS + "\"/>";
        }

        Page rootPage = pageManager.getPage(rootPath);
        if (rootPage == null) {
            LOG.warn("Root page not found at path: {}", rootPath);
            return XML_HEADER + "<urlset xmlns=\"" + SITEMAP_NS + "\"/>";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(XML_HEADER);
        sb.append("<urlset xmlns=\"").append(SITEMAP_NS).append("\">\n");

        addPageToSitemap(rootPage, sb);
        traversePages(rootPage, sb);

        sb.append("</urlset>");
        return sb.toString();
    }

    private void traversePages(Page parentPage, StringBuilder sb) {
        Iterator<Page> children = parentPage.listChildren();
        while (children.hasNext()) {
            Page child = children.next();
            addPageToSitemap(child, sb);
            traversePages(child, sb);
        }
    }

    private void addPageToSitemap(Page page, StringBuilder sb) {
        if (page.isHideInNav()) {
            return;
        }

        if (!includeUnpublished) {
            String replicationAction = page.getProperties().get(REPLICATION_ACTION_PROPERTY, String.class);
            if (!ACTIVATE_ACTION.equals(replicationAction)) {
                return;
            }
        }

        String pagePath = page.getPath();
        String url = externalDomain + pagePath + ".html";

        sb.append("  <url>\n");
        sb.append("    <loc>").append(escapeXml(url)).append("</loc>\n");

        if (page.getLastModified() != null) {
            String lastMod = String.format("%tF", page.getLastModified());
            sb.append("    <lastmod>").append(lastMod).append("</lastmod>\n");
        }

        sb.append("  </url>\n");
    }

    private String escapeXml(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("&", "&amp;")
                     .replace("<", "&lt;")
                     .replace(">", "&gt;")
                     .replace("\"", "&quot;")
                     .replace("'", "&apos;");
    }
}
