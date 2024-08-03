package com.eb.earbee.security.login.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        PrintWriter out = response.getWriter();
        String errorMessage = "로그인 실패: ID 또는 비밀번호를 확인하세요.";
        if (exception.getMessage() != null) {
            errorMessage = exception.getMessage();
        }

        String jsonResponse = String.format("{\"error\": \"%s\"}", errorMessage);
        out.print(jsonResponse);
        out.flush();
    }
}

