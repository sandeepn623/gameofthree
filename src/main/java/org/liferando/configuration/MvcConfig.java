package org.liferando.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class MvcConfig {

    /**
     *  Setting the default locale
     *  the default locale will be used when no Accept-Language is set in the request header
     */

    @Bean
    public Locale locale() {
        Locale locale = new Locale("en","EN");
        Locale.setDefault(locale);
        return locale;
    }



}
