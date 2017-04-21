package com.convertion.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableScheduling
@ComponentScan(basePackages = { "com.convertion.repositories" })
public class DispatcherConfiguration extends WebMvcConfigurerAdapter {
    
}
