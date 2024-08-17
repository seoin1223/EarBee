package com.eb.earbee.security.login.oauth;

import com.eb.earbee.main.service.UserService;
import com.eb.earbee.security.entity.TemporaryUser;
import com.eb.earbee.security.login.basicLogin.PrincipalUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationOauthSuccessHandler1 implements AuthenticationSuccessHandler {

    @Autowired
    public UserService userService;

    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();


        // 사용자 정보 가져오기
        PrincipalUserDetails userDetails = (PrincipalUserDetails) principal;
        TemporaryUser tempUser = userDetails.getTemporaryUser();

        HttpSession session = request.getSession();
        session.setAttribute("principal", principal);
        boolean isUserRegistered = userService.isUser(userDetails.getId());
        // 원래 요청 URL을 가져오기
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String redirectUrl = savedRequest != null ? savedRequest.getRedirectUrl() : "/";

        // 사용자 정보를 기반으로 리다이렉트 처리
        if (isUserRegistered) {
            // 회원가입이 완료된 사용자
            response.sendRedirect(redirectUrl);
        } else {
            // 구글 OAuth2로 인증된 상태지만 회원가입이 완료되지 않은 경우
            response.sendRedirect("/joinOauth2"); // 회원가입 페이지로 리다이렉트
        }
    }
}
