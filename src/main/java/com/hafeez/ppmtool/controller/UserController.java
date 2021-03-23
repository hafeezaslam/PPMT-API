package com.hafeez.ppmtool.controller;

import com.hafeez.ppmtool.domain.User;
import com.hafeez.ppmtool.payload.JwtLoginSuccessResponse;
import com.hafeez.ppmtool.payload.LoginRequest;
import com.hafeez.ppmtool.security.JwtTokenProvider;
import com.hafeez.ppmtool.services.MapValidationErrorService;
import com.hafeez.ppmtool.services.UserService;
import com.hafeez.ppmtool.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static com.hafeez.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
        Optional<ResponseEntity<?>> errorMap = mapValidationErrorService.mapValidationService(result);
        return errorMap.orElseGet(() -> {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                )
            );
            // instead of using
            // SecurityContextHolder.getContext().setAuthentication(authentication);

            // use this code to avoid race condition across multiple threads
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);


            String jwt = TOKEN_PREFIX +  tokenProvider.generateToken(authentication);

            return ResponseEntity.ok(new JwtLoginSuccessResponse(true, jwt));
        });
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {

//        userValidator.validate(user, result);
        Optional<ResponseEntity<?>> errorMap = mapValidationErrorService.mapValidationService(result);
        return errorMap.orElseGet(() -> {
            User newUser = userService.saveUser(user);
            return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
        });
    }
}
