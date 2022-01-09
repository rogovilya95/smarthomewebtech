package htw.webtech.smarthome.controllers;

import htw.webtech.smarthome.dto.ResponseDto;
import htw.webtech.smarthome.dto.user.SignInDto;
import htw.webtech.smarthome.dto.user.SignInResponseDto;
import htw.webtech.smarthome.dto.user.SignUpDto;
import htw.webtech.smarthome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignUpDto signUpDto) {
        return userService.signUp(signUpDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) {
        return userService.signIn(signInDto);
    }

}
