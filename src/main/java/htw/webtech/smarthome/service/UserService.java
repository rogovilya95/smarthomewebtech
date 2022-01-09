package htw.webtech.smarthome.service;

import htw.webtech.smarthome.domain.AuthenticationToken;
import htw.webtech.smarthome.domain.User;
import htw.webtech.smarthome.dto.ResponseDto;
import htw.webtech.smarthome.dto.user.SignInDto;
import htw.webtech.smarthome.dto.user.SignInResponseDto;
import htw.webtech.smarthome.dto.user.SignUpDto;
import htw.webtech.smarthome.exceptions.AuthenticationFailException;
import htw.webtech.smarthome.exceptions.CustomException;
import htw.webtech.smarthome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Transactional
    public ResponseDto signUp(SignUpDto signUpDto) {
        if (Objects.nonNull(userRepository.findByEmail(signUpDto.getEmail()))) {
            throw new CustomException("User already present");
        }

        String encryptedpassword = signUpDto.getPassword();

        try {
            encryptedpassword = hashPassword(signUpDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = new User(signUpDto.getFirstName(), signUpDto.getLastName(),
                signUpDto.getEmail(), encryptedpassword);
        userRepository.save(user);


        final AuthenticationToken authenticationToken = new AuthenticationToken(user);

        authenticationService.saveConfirmationToken(authenticationToken);

        ResponseDto responseDto = new ResponseDto("success", "User has been successfully created");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInResponseDto signIn(SignInDto signInDto) {
        User user = userRepository.findByEmail(signInDto.getEmail());

        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("User is not valid");
        }

        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                throw new AuthenticationFailException("Password is wrong");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if (Objects.isNull(token)) {
            throw new CustomException("Token doesn't exist");
        }

        return new SignInResponseDto("success", token.getToken());
    }
}
