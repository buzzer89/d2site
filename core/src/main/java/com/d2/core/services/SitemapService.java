package com.d2.core.services;

import org.apache.sling.api.resource.ResourceResolver;

/**
 * Service that generates a sitemap XML string for all published pages
 * under a given content root path.
 */
public interface SitemapService {

    /**
     * Generates a sitemap XML for all published pages under the given root path.
     *
     * @param resourceResolver the resource resolver to use for reading pages
     * @param rootPath         the content root path (e.g. /content/d2site)
     * @return sitemap XML as a string
     */
    String generateSitemap(ResourceResolver resourceResolver, String rootPath);
}
