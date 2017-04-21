package com.convertion.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableScheduling
@ComponentScan(basePackages = { "com.convertion.repositories", "com.convertion.services" })
public class DispatcherConfiguration extends WebMvcConfigurerAdapter {
    
}
