package com.xenialsoft.api.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class MessageConfig {

    @Bean
    MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        // 기본 언어는 한국어로 설정
        messageSource.setDefaultLocale(Locale.KOREAN);
        // 메시지가 없는 경우 코드 그대로 사용 설정
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    /**
     * HTTP 헤더의 'Accept-Language'를 사용
     * 
     * @return
     */
    @Bean
    LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }

}
