package com.welab.k8s_api_gateway.security.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

// 인증은 되었지만, 권한 없는 사용자가 접근했을 경우 핸들러 (403: 인가 실패)
// HandlerExceptionResolver를 통해 예외를 일관된 방식으로 처리
@Component
@RequiredArgsConstructor
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    private final HandlerExceptionResolver handlerExceptionResolver;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
    }
}

// 사용자가 접근 권한 없는 리소스 요청 -> AccessDeniedException 예외 발생
// -> Spring Security가 RestAccessDeniedHandler의 handle() 메서드를 호출함.
// handlerExceptionResolver.resolveException()을 호출해, 애플리케이션 전역 예외 처리 방식에 따라 응답 처리
// @RestControllerAdvice를 강제로 트리거
