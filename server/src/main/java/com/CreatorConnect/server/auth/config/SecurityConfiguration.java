package com.CreatorConnect.server.auth.config;

import com.CreatorConnect.server.auth.filter.JwtAuthenticationFilter;
import com.CreatorConnect.server.auth.filter.JwtVerificationFilter;
import com.CreatorConnect.server.auth.handler.*;
import com.CreatorConnect.server.auth.jwt.JwtTokenizer;
import com.CreatorConnect.server.auth.jwt.TokenService;
import com.CreatorConnect.server.auth.oauth.handler.OAuth2MemberSuccessHandler;
import com.CreatorConnect.server.auth.utils.CustomAuthorityUtils;
import com.CreatorConnect.server.member.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured("ROLE_ADMIN") 활성화
public class SecurityConfiguration {

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final OAuth2MemberSuccessHandler oAuth2MemberSuccessHandler;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer, CustomAuthorityUtils authorityUtils, OAuth2MemberSuccessHandler oAuth2MemberSuccessHandler, MemberRepository memberRepository, TokenService tokenService) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.oAuth2MemberSuccessHandler = oAuth2MemberSuccessHandler;
        this.memberRepository = memberRepository;
        this.tokenService = tokenService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource()); // cors 종영
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .cors(Customizer.withDefaults()) // CORS 설정 추가 (corsConfigurationSource Bean)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/h2/**").permitAll()
                        .antMatchers(HttpMethod.OPTIONS).permitAll()
                        .antMatchers(HttpMethod.POST, "/api/signup/**", "/api/login", "/api/refresh-token").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/api/refresh-token").permitAll()
                        .antMatchers(HttpMethod.GET, "/", "/api/search/**", "/api/keyword/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/login/**", "/auth/**", "/api/member/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/freeboard/**", "/api/freeboards/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/feedbackboard/**", "/api/feedbackboards/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/promotionboard/**","/api/promotionboards/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/jobboard/**","/api/jobboards/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/notice/**","/api/notices").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/jobcategory/**","/api/jobcategories").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/feedbackcategory/**","/api/feedbackcategories").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/category/**","/api/categories").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/youtubevideos/**").permitAll()
                        .anyRequest().authenticated()
                        )
                .oauth2Login()
                .successHandler(oAuth2MemberSuccessHandler)
                ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*")); // cors 종영
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "HEAD", "OPTIONS"));
        configuration.addExposedHeader("Authorization");
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, tokenService);
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler(memberRepository));
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

            builder
                    .addFilter(jwtAuthenticationFilter) // Spring Security Filter Chain 에 JwtAuthenticationFilter 추가
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class) // JwtAuthenticationFilter 에서 로그인 인증 후, 발급 받은 JWT가 요청의 request header(Authorization)에 포함되어 있을 경우 동작
                    .addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
        }
    }
}
