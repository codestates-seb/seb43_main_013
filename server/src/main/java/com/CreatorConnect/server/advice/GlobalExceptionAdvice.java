package com.CreatorConnect.server.advice;

import com.CreatorConnect.server.auth.jwt.JwtTokenizer;
import com.CreatorConnect.server.auth.jwt.TokenService;
import com.CreatorConnect.server.auth.jwt.refreshtoken.RefreshTokenController;
import com.CreatorConnect.server.auth.jwt.refreshtoken.RefreshTokenRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.JwtVerificationException;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice // 전역 예외 처리
public class GlobalExceptionAdvice {

    private final JwtTokenizer jwtTokenizer;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    public GlobalExceptionAdvice(JwtTokenizer jwtTokenizer, MemberRepository memberRepository, TokenService tokenService, RefreshTokenRepository refreshTokenRepository) {
        this.jwtTokenizer = jwtTokenizer;
        this.memberRepository = memberRepository;
        this.tokenService = tokenService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        final ErrorResponse response = ErrorResponse.of(e.getBindingResult());
        // 유효성 검사에 실패한 필드와 해당 오류
        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(
            ConstraintViolationException e) {
        final ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());
        // 제약 조건 위반과 관련된 오류 정보
        return response;
    }

    @ExceptionHandler
    public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());
        // BusinessLogicException 예외 처리
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode()
                .getStatus()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        // 요청된 HTTP 메서드가 허용되지 않을 때
        return ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        // 요청의 본문이 올바르지 않을 때
        return ErrorResponse.of(HttpStatus.BAD_REQUEST,"Required request body is missing");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        // 요청 매개변수가 누락되었을 때
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        log.error("# handle Exception", e);
        // TODO 애플리케이션의 에러 !
        //      1. 에러 로그를 로그에 기록
        //      2. 관리자에게 이메일이나 카카오톡, 슬랙 등으로 알려주는 로직 필요
        // 애플리케이션의 내부 오류 처리
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JwtVerificationException.class)
    public ResponseEntity<String> handleJwtVerificationException(JwtVerificationException exception,
                                                                 HttpServletRequest request) {
        log.error("Jwt verification failed", exception);
        // JWT 검증 실패
        RefreshTokenController refreshTokenController = new RefreshTokenController(jwtTokenizer, memberRepository, tokenService, refreshTokenRepository);
        return refreshTokenController.refreshAccessToken(request);
    }

}