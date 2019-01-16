package com.eagletsoft.boot.projectname;

import com.eagletsoft.boot.framework.common.security.AccessCut;
import com.eagletsoft.boot.framework.common.utils.ApplicationUtils;
import com.eagletsoft.boot.framework.common.validation.JSValidationFactoryBean;
import com.eagletsoft.boot.framework.common.validation.ValidRefCut;
import com.eagletsoft.boot.framework.data.json.ExtMapperFactory;
import com.eagletsoft.boot.framework.data.json.load.ILoader;
import com.eagletsoft.boot.framework.data.json.load.impl.DummyLoader;
import com.eagletsoft.boot.framework.data.json.load.impl.SqlLoader;
import com.eagletsoft.boot.framework.data.json.plugin.ExtMappingJackson2HttpMessageConverter;
import com.eagletsoft.boot.projectname.api.ApiServlet;
import com.eagletsoft.boot.projectname.api.ProjectApiExceptionResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.HashMap;
import java.util.Map;

@org.springframework.boot.autoconfigure.SpringBootApplication
@ImportResource("classpath:spring/applicationContext*.xml")
@ComponentScan({"com.eagletsoft.boot.framework", "com.eagletsoft.boot.projectname", "com.eagletsoft.boot.auth", "com.eagletsoft.boot.profile"})
public class ProjectnameApplication extends SpringBootServletInitializer {

    private static Logger LOG = LoggerFactory.getLogger(ProjectnameApplication.class);
    public static void main(String[] args) {
        ApplicationUtils.CONTEXT = SpringApplication.run(ProjectnameApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

    @Bean
    public ServletRegistrationBean apiServlet() {
        DispatcherServlet servlet = new ApiServlet();
        servlet.setThrowExceptionIfNoHandlerFound(true);
        ServletRegistrationBean bean = new ServletRegistrationBean(servlet, "/*");
        Map<String, String> params = new HashMap<>();
        params.put("detectAllHandlerExceptionResolvers", "true");
        params.put("contextConfigLocation", "classpath:spring/apiServlet.xml");
        bean.setInitParameters(params);
        bean.setLoadOnStartup(1);
        return bean;
    }
/*
    @Order(1)
    @Bean
    public FilterRegistrationBean corsFilterBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new CorsFilter());

        bean.addUrlPatterns("/*");
        bean.addInitParameter("cors.allowed.origins","*");
        bean.addInitParameter("cors.allowed.methods","GET,POST,HEAD,OPTIONS,PUT");
        bean.addInitParameter("cors.allowed.headers","Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
        bean.addInitParameter("cors.exposed.headers","Access-Control-Allow-Origin,Access-Control-Allow-Credentials");
        bean.addInitParameter("cors.allowed.origins","*");
        return bean;
    }
    @Order(2)
    @Bean
    public FilterRegistrationBean characterEncodingFilterBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new CharacterEncodingFilter());
        bean.addUrlPatterns("/*");

        Map<String, String> params = new HashMap<>();
        bean.addInitParameter("encoding","UTF-8");
        bean.addInitParameter("forceEncoding","true");
        return bean;
    }
*/

    @Order(3)
    @Bean
    public FilterRegistrationBean shiroFilterBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new DelegatingFilterProxy("shiroFilter"));
        bean.addInitParameter("targetFilterLifecycle","true");
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Bean
    public HandlerExceptionResolver apiExceptionResolver() {
        return new ProjectApiExceptionResolver();
    }


    @Bean
    public AccessCut accessCut() {
        return new AccessCut();
    }

    @Bean
    public JSValidationFactoryBean jsValidationFactoryBean() {
        return new JSValidationFactoryBean();
    }


    @Bean
    public ValidRefCut validRefCut() {
        ValidRefCut cut = new ValidRefCut();
        return cut;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new ExtMappingJackson2HttpMessageConverter();

        converter.setObjectMapper(ExtMapperFactory.create());
        return converter;
    }

    @Bean
    public ILoader sqlLoader() {
        return new SqlLoader();
    }


    @Bean
    public ILoader dummyLoader() {
        return new DummyLoader();
    }
}
