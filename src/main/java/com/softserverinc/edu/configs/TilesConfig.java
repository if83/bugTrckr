package com.softserverinc.edu.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

/**
 * Configuration for Apache Tiles
 */
@Configuration
public class TilesConfig extends WebMvcConfigurerAdapter {

    public static final Logger LOGGER = LoggerFactory.getLogger(TilesConfig.class);

    /**
     * Tells to the Tiles context a configuration file
     *
     * @return object with tiles configuration
     */
    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[]{"/WEB-INF/layouts/tiles-definitions.xml"});
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }

    /**
     * Let to resolve of views to Tiles template framework
     *
     * @return viewResolver
     */
    @Bean
    public UrlBasedViewResolver viewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(TilesView.class);
        return viewResolver;
    }

}
