package com.badomega.hims.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/db").setViewName("diseases");
        registry.addViewController("/users").setViewName("users");
        registry.addViewController("/beacons").setViewName("beacon");
        registry.addViewController("/persons").setViewName("persons");
        registry.addViewController("/monitoring").setViewName("monitoring");
        registry.addViewController("/rule_breaks").setViewName("rule_breaks");
        registry.addViewController("/about").setViewName("about-page");
        registry.addViewController("/contact").setViewName("contact");
    }
}
