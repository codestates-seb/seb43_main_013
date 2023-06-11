package com.CreatorConnect.server.auth.config;

import com.CreatorConnect.server.auth.filter.JwtAuthenticationFilter;
import com.CreatorConnect.server.auth.filter.JwtLogoutFilter;
import com.CreatorConnect.server.auth.filter.JwtVerificationFilter;
import com.CreatorConnect.server.auth.handler.*;
import com.CreatorConnect.server.auth.jwt.JwtTokenizer;
import com.CreatorConnect.server.auth.jwt.TokenService;
import com.CreatorConnect.server.auth.oauth.handler.OAuth2MemberSuccessHandler;
import com.CreatorConnect.server.auth.utils.CustomAuthorityUtils;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.redis.RedisService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
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
@EnableWebSecurity //  ( Spring Security) 웹 보안 활성화
@EnableGlobalMethodSecurity(securedEnabled = true) // 메소드 수준의 보안 설정 활성화 , @Secured("ROLE_ADMIN") 활성화
public class SecurityConfiguration {

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final OAuth2MemberSuccessHandler oAuth2MemberSuccessHandler;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;
    private final RedisService redisService;
    private final RedisTemplate redisTemplate;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer, CustomAuthorityUtils authorityUtils, OAuth2MemberSuccessHandler oAuth2MemberSuccessHandler, MemberRepository memberRepository, TokenService tokenService, RedisService redisService, RedisTemplate redisTemplate) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.oAuth2MemberSuccessHandler = oAuth2MemberSuccessHandler;
        this.memberRepository = memberRepository;
        this.tokenService = tokenService;
        this.redisService = redisService;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // SecurityFilterChain 을 생성하여 반환
        http.cors().configurationSource(corsConfigurationSource()); // CORS 구성 소스를 사용하여 CORS 설정
        http
                .headers().frameOptions().sameOrigin() // X-Frame-Options 헤더를 설정하여 동일 출처에서만 프레임 로드 가능
                .and()
                .cors(Customizer.withDefaults()) // 기본적인 CORS 설정을 추가 (corsConfigurationSource Bean 사용)
                .csrf().disable() // CSRF(Cross-Site Request Forgery) 보호 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER) // 세션 관리 정책을 NEVER 로 설정하여 세션을 생성하지 않음
                .and()
                .formLogin().disable() // 폼 기반 로그인 비활성화
                .httpBasic().disable() // HTTP 기본 인증 비활성화
//                .logout() // 폼(세션) 로그아웃 사용하지 않음
//                .logoutUrl("/api/logout")
//                .logoutSuccessUrl("/")  // 리다이렉트 - http
//                .invalidateHttpSession(true) // HTTP 세션 무효화
//                .deleteCookies("JSESSIONID", "remember-me") // 쿠키 삭제
//                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint()) // 인증 진입점 (인증되지 않은 사용자의 접근 시 처리) 설정
                .accessDeniedHandler(new MemberAccessDeniedHandler()) // 접근 거부 핸들러 (인가되지 않은 사용자의 접근 시 처리) 설정
                .and()
                .apply(new CustomFilterConfigurer()) // 사용자 정의 필터 설정 적용
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/h2/**").permitAll()
                        .antMatchers(HttpMethod.OPTIONS).permitAll()
                        .antMatchers(HttpMethod.POST, "/api/signup/**", "/api/login", "/api/refresh-token").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/api/logout").permitAll()
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
        configuration.setAllowedOrigins(Collections.singletonList("*")); // 모든 origin 을 허용
        configuration.addAllowedOriginPattern("*"); // 모든 origin 패턴을 허용
        configuration.setAllowedHeaders(Collections.singletonList("*")); // 모든 헤더를 허용
        configuration.setExposedHeaders(Collections.singletonList("*")); // 모든 노출 헤더를 허용
        configuration.setAllowCredentials(true); // 자격 증명(credential) 을 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "HEAD", "OPTIONS")); // 허용하는 HTTP 메서드 목록
        configuration.addExposedHeader("Authorization, Refresh-Token"); // 노출할 헤더 추가
        configuration.setMaxAge(3600L); // 사전 검증(preflight) 요청의 캐시(max-age) 시간 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // UrlBasedCorsConfigurationSource 에 등록
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 구성을 적용

        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> { // HttpSecurity 빌더에 필터를 추가
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class); // AuthenticationManager 를 가져와서 인증 매니저로 설정

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, tokenService, redisService, jwtTokenizer);
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler(memberRepository));
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils, redisTemplate);

            JwtLogoutFilter jwtLogoutFilter = new JwtLogoutFilter(jwtTokenizer, redisService);

            builder
                    .addFilter(jwtAuthenticationFilter) // Spring Security Filter Chain 에 JwtAuthenticationFilter 추가
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class) // JwtAuthenticationFilter 이후에 JwtVerificationFilter 추가
                    .addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class) // OAuth2LoginAuthenticationFilter 이후에 JwtVerificationFilter 추가
                    .addFilterAfter(jwtLogoutFilter, JwtVerificationFilter.class); // JwtVerificationFilter 이후에 JwtLogoutFilter 추가
        }
    }
}
