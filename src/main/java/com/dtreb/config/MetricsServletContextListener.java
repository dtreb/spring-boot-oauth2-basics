package com.dtreb.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * See {@link MetricsConfiguration} for usage details.
 *
 * @author dtreb
 */
public class MetricsServletContextListener implements ServletContextListener {

    private MetricRegistry metricRegistry;

    public MetricsServletContextListener(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute(MetricsServlet.METRICS_REGISTRY, metricRegistry);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
