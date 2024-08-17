package com.eb.earbee.security.provider;

import com.eb.earbee.security.dto.AccountContext;
import com.eb.earbee.security.token.AjaxAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("restAuthenticationProvider")

public class RestAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public RestAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Extract credentials from the authentication token
        String username = (String) authentication.getPrincipal();  // `getPrincipal` returns username in this case
        String password = (String) authentication.getCredentials(); // `getCredentials` returns password

        // Load UserDetails from the UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Check if the provided password matches the stored password
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        // Create and return a new AjaxAuthenticationToken with user's authorities
        return new AjaxAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Support for AjaxAuthenticationToken
        return AjaxAuthenticationToken.class.isAssignableFrom(authentication);
    }
}