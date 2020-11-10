package org.liferando.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.liferando"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(metaInfo())
                .tags(new Tag("Game Of Three Controller", "A REST based mathematical game engine which showcases the magic of number 3"));
    }

    private ApiInfo metaInfo() {

        return new ApiInfo(
                "Game Of Three",
                "Game API.",
                "1.0",
                "Terms of Service URL",
                new Contact("Sandeep Nanjundaswamy", "https://github.com/sandeepn623", "sandeepn623@gmail.com"),
                "<license name here>",
                "https://en.wikipedia.org/wiki/Software_license",
                Arrays.asList());
    }

}
