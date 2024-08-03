package com.eb.earbee.security.login.oauth;


import com.eb.earbee.main.entity.User;
import com.eb.earbee.main.repository.UserRepository;
import com.eb.earbee.security.entity.TemporaryUser;
import com.eb.earbee.security.login.basicLogin.PrincipalUserDetails;
import com.eb.earbee.security.repository.TemporaryUserRespository;
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
    private UserRepository userRepository;

    @Autowired
    private TemporaryUserRespository temporaryUserRespository;

    /* Oauth 후처리*/
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oauth2User.getAttribute("sub");
        String id = provider+"_"+providerId;
        String pwd = encoder.encode(id);
        String email = oauth2User.getAttribute("email");
        String role = "ROLE_USER";
        String username = oauth2User.getAttribute("name");

        User user1 = userRepository.findById(id);
        TemporaryUser user2 = temporaryUserRespository.findById(id);
        if (user1 != null) {
            return new PrincipalUserDetails(user1, oauth2User.getAttributes());
        }

        if (user2 != null) {
            return new PrincipalUserDetails(user2, oauth2User.getAttributes());
        }

        TemporaryUser newTempUser = TemporaryUser.builder().provider(provider).password(pwd).providerId(providerId).id(id).email(email).role(role).username(username).build();
        user2 = temporaryUserRespository.save(newTempUser);
        return new PrincipalUserDetails(user2,oauth2User.getAttributes());
    }
}
