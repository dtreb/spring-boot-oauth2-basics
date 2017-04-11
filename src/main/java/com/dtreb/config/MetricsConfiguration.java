package com.dtreb.config;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Dropwizard Metrics library integration configuration.
 *
 * @author dtreb
 */
@Configuration
@EnableMetrics
public class MetricsConfiguration extends MetricsConfigurerAdapter {

    @Autowired
    private MetricRegistry metricRegistry;

    @PostConstruct
    public void init() {
        configureReporters(metricRegistry);
    }

    @Bean
    public MetricsServletContextListener metricsServletContextListener(MetricRegistry metricRegistry) {
        return new MetricsServletContextListener(metricRegistry);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new MetricsServlet(),"/metrics/*");
    }

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        registerReporter(JmxReporter.forRegistry(metricRegistry).build()).start();
    }
}
