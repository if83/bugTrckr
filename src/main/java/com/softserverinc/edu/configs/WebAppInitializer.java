package com.softserverinc.edu.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Configuration of dispatcher-servlet
 */
public class WebAppInitializer implements WebApplicationInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(WebAppInitializer.class);

    @Override
    public void onStartup(ServletContext container) throws ServletException {

        LOGGER.debug("WebAppInitializer.onStartup is called");

        // Registering WebConfig class for further servlet configuration
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(WebConfig.class);

        // manage the lifecycle of rootcontext
        container.addListener(new ContextLoaderListener(rootContext));

        //activate spring security by configuring filter
        //all requests will path through this filter
        DelegatingFilterProxy filter = new DelegatingFilterProxy("springSecurityFilterChain");

        // Define and register a dispatcher servlet that can manage all servlets
        DispatcherServlet dispatcherServlet = new DispatcherServlet(rootContext);
        ServletRegistration.Dynamic registration = container.addServlet("dispatcherServlet", dispatcherServlet);

        //adding a filter to container that handles all requests
        container.addFilter("springSecurityFilterChain", filter).addMappingForUrlPatterns(null, false, "/*");

        // allow lazy loading in web views despite the original transactions already being completed
        container.addFilter("OpenEntityManagerInViewFilter", OpenEntityManagerInViewFilter.class)
                .addMappingForUrlPatterns(null, false, "*");

        //filter for encoding all request parameters to unicode
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        container.addFilter("encode", characterEncodingFilter)
                .addMappingForUrlPatterns(null, false, "/*");

        // load servlet only once
        registration.setLoadOnStartup(1);

        // add mapping this servlet to all requests
        registration.addMapping("/");
    }
}