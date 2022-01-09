package htw.webtech.smarthome.service;

import htw.webtech.smarthome.domain.AuthenticationToken;
import htw.webtech.smarthome.domain.User;
import htw.webtech.smarthome.exceptions.AuthenticationFailException;
import htw.webtech.smarthome.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepository tokenRepository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }

    public void authenticate(String token) throws AuthenticationFailException {
        if(Objects.isNull(token)) {
            throw new AuthenticationFailException("token not present");
        }
        if(Objects.isNull(getUser(token))) {
            throw new AuthenticationFailException("token not valid");
        }
    }

    public User getUser(String token) {
        final AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
        if(Objects.isNull(authenticationToken)) {
            return null;
        }
        return authenticationToken.getUser();
    }


}
