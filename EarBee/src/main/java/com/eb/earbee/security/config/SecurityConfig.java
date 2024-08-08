package com.eb.earbee.security.config;


//import com.eb.earbee.security.oauth.PrincipalOauth2UserService;
import com.eb.earbee.security.login.oauth.CustomAuthenticationFailureHandler;
import com.eb.earbee.security.login.oauth.CustomAuthenticationOauthSuccessHandler1;
import com.eb.earbee.security.login.oauth.CustomAuthenticationSuccessHandler1;
import com.eb.earbee.security.login.oauth.PrincipalOauth2UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final PrincipalOauth2UserService principalOauth2UserService;

    public SecurityConfig(PrincipalOauth2UserService principalOauth2UserService) {
        this.principalOauth2UserService = principalOauth2UserService;
    }


    @Bean
    public AuthenticationSuccessHandler customAuthenticationOauthSuccessHandler() {
        return new CustomAuthenticationOauthSuccessHandler1();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler1();
    }

    /* ajax를 위한 SecurityFilerChain 추후 사용할 수 있음 아직 아님*/
    @Bean
    public SecurityFilterChain restScurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/login/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**","/image/**", "/js/**","/favicon.*","/*/icon-*").permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/myPage")).hasAnyRole("ADMIN","MANAGER", "USER")
                        .anyRequest().permitAll()
                ).sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 생성 정책
                );
        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(new AntPathRequestMatcher("/login", "/login-process")).permitAll()
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/earbee/**")).hasAnyRole("ADMIN", "MANAGER","USER")
                                .requestMatchers(new AntPathRequestMatcher("/myPage")).hasAnyRole("ADMIN","MANAGER", "USER")
                                .anyRequest().permitAll()

                )
                .formLogin(f -> f
                        .loginPage("/login")
                        .defaultSuccessUrl("/",false)
                        .failureHandler(new CustomAuthenticationFailureHandler())
                        .failureUrl("/")
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login-process")
                        .successHandler(customAuthenticationSuccessHandler())
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()

                ).oauth2Login(oau ->oau
                        .loginPage("/login")
                        .defaultSuccessUrl("/",false)
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(principalOauth2UserService))
                        .successHandler(customAuthenticationOauthSuccessHandler())
                ).requestCache(cache -> cache.requestCache(new HttpSessionRequestCache()));
        return http
                .build();
    }




}
