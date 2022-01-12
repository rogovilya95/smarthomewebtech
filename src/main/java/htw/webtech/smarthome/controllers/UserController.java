package htw.webtech.smarthome.controllers;

import htw.webtech.smarthome.domain.User;
import htw.webtech.smarthome.dto.ResponseDto;
import htw.webtech.smarthome.dto.user.SignInDto;
import htw.webtech.smarthome.dto.user.SignInResponseDto;
import htw.webtech.smarthome.dto.user.SignUpDto;
import htw.webtech.smarthome.exceptions.AuthenticationFailException;
import htw.webtech.smarthome.repository.UserRepository;
import htw.webtech.smarthome.service.AuthenticationService;
import htw.webtech.smarthome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/all")
    public List<User> findAllUser(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        return userRepository.findAll();
    }

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignUpDto signUpDto) {
        return userService.signUp(signUpDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) {
        return userService.signIn(signInDto);
    }

}
