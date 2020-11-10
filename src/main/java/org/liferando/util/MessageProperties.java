package org.liferando.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageProperties {

    private ResourceBundleMessageSource messageSource;


    @Autowired
    MessageProperties(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     *  This method will return the message which is set for the given locale
     *  The locale is set in the Accept-Language header of the request
     *  If not the default locale will be used
     *
     * @param msgCode ResponseCode
     * @return locale message
     */
    public String toLocale(String msgCode, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, args, "No message found for given Code.", locale);
    }
}
