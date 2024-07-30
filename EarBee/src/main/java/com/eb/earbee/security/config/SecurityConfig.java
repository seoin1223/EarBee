package com.eb.earbee.security.config;


//import com.eb.earbee.security.oauth.PrincipalOauth2UserService;
import com.eb.earbee.security.login.oauth.CustomAuthenticationSuccessHandler1;
import com.eb.earbee.security.login.oauth.PrincipalOauth2UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;

    public SecurityConfig(PrincipalOauth2UserService principalOauth2UserService) {
        this.principalOauth2UserService = principalOauth2UserService;
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler1();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(new AntPathRequestMatcher("/login", "/login-process")).permitAll()
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/myPage")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/earbee/**")).hasAnyRole("ADMIN", "MANAGER","USER")
                                .anyRequest().permitAll()

                )
                .formLogin(f -> f
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/user/login?fail")
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login-process")
                ).logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")

                ).oauth2Login(oau ->oau
                        .loginPage("/login")
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(principalOauth2UserService))
                        .successHandler(customAuthenticationSuccessHandler())
                );
        return http
                .build();
    }


}
