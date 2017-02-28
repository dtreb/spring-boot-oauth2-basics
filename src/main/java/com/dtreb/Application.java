package com.dtreb;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Main application entry point.
 *
 * @author dtreb
 */
@EnableSwagger2
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class Application {

    /**
     * To enable endpoints parameters validation using annotations.
     * @return {@link MethodValidationPostProcessor}
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    /**
     * To simplify transformation between POJO entities and JSON in REST endpoints.
     * E.g. lazy child entities will be just skipped instead of throwing exception.
     * @return {@link Jackson2ObjectMapperBuilder}
     */
    @Bean
    public Jackson2ObjectMapperBuilder configureObjectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .modulesToInstall(Hibernate4Module.class);
    }

    /**
     * Allows swagger2 to check exisitng endpoints and provide UI with usage info.
     * @return {@link Docket}
     * @see <a href="http://swagger.io/">http://swagger.io/</a>
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
