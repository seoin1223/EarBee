package com.eb.earbee.security.filters;

import com.eb.earbee.security.dto.AccountDto;
import com.eb.earbee.security.token.RestAuthenticationToken;
import com.eb.earbee.util.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public RestAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/login","POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!HttpMethod.POST.name().equals(request.getMethod()) || !WebUtil.isAjax(request)) {
            System.out.println("error");
            throw new IllegalArgumentException("Authentication method not supported");
        }
        System.out.println(request.getReader());
        AccountDto accountDto = objectMapper.readValue(request.getReader(), AccountDto.class);

        if(!StringUtils.hasText(accountDto.getId())||!StringUtils.hasText(accountDto.getPassword())){
            throw new AuthenticationServiceException("Invalid username or password");
        }

        RestAuthenticationToken restAuthenticationToken = new RestAuthenticationToken(accountDto.getId(),accountDto.getPassword());
        System.out.println("정상적으로 됨");
        return getAuthenticationManager().authenticate(restAuthenticationToken);
    }
}

