package com.eb.earbee.security.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(new AntPathRequestMatcher("/login", "/login-process")).permitAll()
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/earbee/**")).hasAnyRole("ADMIN", "MANAGER","USER")
                                .anyRequest().permitAll()

                )
                .formLogin(f -> f
                        .loginPage("/login")
                        .defaultSuccessUrl("/earbee")
                        .failureUrl("/user/login?fail")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login-process")
                ).logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );






        return http
                .build();
    }
}
