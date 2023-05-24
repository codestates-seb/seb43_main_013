package com.CreatorConnect.server.auth.config;

import com.CreatorConnect.server.auth.filter.HttpServletWrappingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.debug.DebugFilter;

@Configuration
public class WebConfiguration {
    @Bean
    public FilterRegistrationBean<HttpServletWrappingFilter> firstFilterRegister()  {
        FilterRegistrationBean<HttpServletWrappingFilter> registrationBean =
                new FilterRegistrationBean<>(new HttpServletWrappingFilter());
        registrationBean.setOrder(Integer.MIN_VALUE);

        return registrationBean;

    }

}