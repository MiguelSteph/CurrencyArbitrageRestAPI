package com.convertion.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@EnableScheduling
@ComponentScan(basePackages = { "com.convertion.repositories", "com.convertion.services", "com.convertion.rest" })
public class DispatcherConfiguration extends WebMvcConfigurerAdapter {
    
    @Bean
    public View getView() {
        return new MappingJackson2JsonView();
    }
    
}
