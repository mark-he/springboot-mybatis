package com.eagletsoft.boot.projectname;

import com.eagletsoft.boot.framework.api.validation.JSValidInterceptor;
import com.eagletsoft.boot.framework.common.validation.JSValidationFactoryBean;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    JSValidInterceptor jsValidInterceptor;

    @Bean
    public JSValidationFactoryBean jsValidationFactoryBean(){
        JSValidationFactoryBean localValidatorFactoryBean = new JSValidationFactoryBean();
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        return localValidatorFactoryBean;
    }

    @Bean
    public JSValidInterceptor jsValidInterceptor(JSValidationFactoryBean jsValidationFactoryBean) {
        return new JSValidInterceptor(jsValidationFactoryBean);
    }

    @Override
    public Validator getValidator() {
        return jsValidationFactoryBean();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jsValidInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH");
    }
}
