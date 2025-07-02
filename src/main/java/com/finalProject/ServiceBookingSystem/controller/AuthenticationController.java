package com.finalProject.ServiceBookingSystem.controller;

import com.finalProject.ServiceBookingSystem.dto.AuthenticationRequest;
import com.finalProject.ServiceBookingSystem.dto.SignupRequestDTO;
import com.finalProject.ServiceBookingSystem.dto.UserDto;
import com.finalProject.ServiceBookingSystem.entity.User;
import com.finalProject.ServiceBookingSystem.repository.UserRepository;
import com.finalProject.ServiceBookingSystem.services.authentication.AuthService;
import com.finalProject.ServiceBookingSystem.services.jwt.UserDetailsServiceImpl;
import com.finalProject.ServiceBookingSystem.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController

public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public static final String Token_PREFIX = "Bearer";

    public static final String HEADER_String = "Authorization";

    @PostMapping("/client/sign-up")


    public ResponseEntity<?> signupClient(@RequestBody SignupRequestDTO signupRequestDTO) {
        // Add this debug line to check Lombok generation

        if (authService.presentByEmail(signupRequestDTO.getEmail())) {
            return new ResponseEntity<>("Client already exists with this Email!", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto createdUser = authService.signupClient(signupRequestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @PostMapping("/company/sign-up")


    public ResponseEntity<?> signupCompany(@RequestBody SignupRequestDTO signupRequestDTO) {
        // Add this debug line to check Lombok generation

        if (authService.presentByEmail(signupRequestDTO.getEmail())) {
            return new ResponseEntity<>("Company already exists with this Email!", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto createdUser = authService.signupCompany(signupRequestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }
    @PostMapping({"/authenticate"})
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) throws IOException, JSONException {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),authenticationRequest.getPassword()
            ));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password",e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(user.getEmail(), user.getRole().name());


        response.getWriter().write(new JSONObject()
                .put("token", jwt)
                .put("userId", user.getId())
                .put("role", user.getRole())
                .toString()
        );
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers",
                "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");

        response.addHeader(HEADER_String, Token_PREFIX + " " + jwt);


    }
}
