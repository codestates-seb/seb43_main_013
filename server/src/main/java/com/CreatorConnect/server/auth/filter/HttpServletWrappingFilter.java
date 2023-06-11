package com.CreatorConnect.server.auth.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpServletWrappingFilter extends OncePerRequestFilter { // 서블릿 기반 필터, 요청과 응답을 가로채고 조작
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException { // 모든 요청에 대해 실행, 서블릿 컨테이너가 필터를 호출할 때마다 호출

        // CORS 관련 헤더 설정
        response.setHeader("Access-Control-Allow-Origin", "https://www.hard-coding.com"); // 접근을 허용할 도메인을 설정
        response.setHeader("Access-Control-Allow-Methods","POST, GET, DELETE, PATCH, OPTIONS"); // 허용할 HTTP 메서드를 설정
        response.setHeader("Access-Control-Max-Age","3600"); //  Preflight 요청의 캐싱 시간을 설정
        response.setHeader("Access-Control-Allow-Headers","Content-Type, x-requested-with, Authorization, Access-Control-Allow-Origin, Refresh-Token"); // 허용할 요청 헤더를 설정
        response.setHeader("Access-Control-Allow-Credentials","true"); // 인증 정보를 포함하여 요청을 허용할지 여부를 설정
        response.setHeader("Access-Control-Expose-Headers", "Content-Length"); // 브라우저가 액세스할 수 있는 추가적인 응답 헤더를 설정

        // 요청과 응답을 캐싱하기 위해 래핑
        ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);
        // 필터 이후에도 요청과 응답의 내용을 읽고 조작

        // 다음 필터 또는 서블릿으로 요청 전달
        filterChain.doFilter(wrappingRequest, wrappingResponse);

        // 캐싱된 응답 내용을 실제 응답으로 복사
        wrappingResponse.copyBodyToResponse();
    }
}