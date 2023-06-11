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
    public FilterRegistrationBean<HttpServletWrappingFilter> firstFilterRegister() {
        FilterRegistrationBean<HttpServletWrappingFilter> registrationBean =
                new FilterRegistrationBean<>(new HttpServletWrappingFilter());
        registrationBean.setOrder(Integer.MIN_VALUE); // 필터의 우선 순위 설정 -  가장 낮은 우선 순위

        return registrationBean;
    }

    // FilterRegistrationBean - 필터를 등록하고 구성
    // HttpServletWrappingFilter - 서블릿 기반 필터를 스프링 필터로 래핑
}