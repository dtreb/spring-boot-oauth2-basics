package com.dtreb.config;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.CachedThreadStatesGaugeSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private HealthCheckRegistry healthCheckRegistry;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        configureReporters(metricRegistry);
    }

    @Bean
    public MetricsServletContextListener metricsServletContextListener(MetricRegistry metricRegistry, HealthCheckRegistry healthCheckRegistry) {
        return new MetricsServletContextListener(metricRegistry, healthCheckRegistry);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new MetricsServlet(),"/metrics/*");
    }

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        metricRegistry.register("memory", new MemoryUsageGaugeSet());
        metricRegistry.register("threads", new CachedThreadStatesGaugeSet(10, TimeUnit.SECONDS));
        if(dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).setMetricRegistry(metricRegistry);
        }
        registerReporter(JmxReporter.forRegistry(metricRegistry).build()).start();
    }
}
