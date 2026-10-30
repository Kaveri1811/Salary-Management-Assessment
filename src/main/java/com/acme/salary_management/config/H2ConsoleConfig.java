package com.acme.salary_management.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.h2.server.web.JakartaWebServlet;

@Configuration
public class H2ConsoleConfig {

    @Bean
    ServletRegistrationBean<JakartaWebServlet> h2Console() {
        ServletRegistrationBean<JakartaWebServlet> registration =
                new ServletRegistrationBean<>(new JakartaWebServlet(), "/h2-console/*");

        registration.setName("H2Console");
        registration.setLoadOnStartup(1);

        return registration;
    }
}