package com.eb.earbee.security.oauth;


import com.eb.earbee.main.entity.User;
import com.eb.earbee.main.repository.UserRepository;
import com.eb.earbee.security.oauth.basicLogin.PrincipalUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserRepository userRepository;


    /* Oauth 후처리*/
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauth2User = super.loadUser(userRequest);


        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oauth2User.getAttribute("sub");
        String id = provider+"_"+providerId;
        String pwd = encoder.encode(id);
        String emaiil = oauth2User.getAttribute("email");
        String role = "ROLE_USER";
        String username = oauth2User.getAttribute("name");
        User userEntity = userRepository.findById(id);

        if(userEntity == null) {
            userEntity= User.builder().id(id).password(pwd).email(emaiil).provider(provider).username(username).providerId(providerId).role(role).build();
            userRepository.save(userEntity);
        }

        return new PrincipalUserDetails(userEntity,oauth2User.getAttributes());
    }
}
