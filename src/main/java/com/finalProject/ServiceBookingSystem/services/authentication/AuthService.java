package com.finalProject.ServiceBookingSystem.services.authentication;

import com.finalProject.ServiceBookingSystem.dto.SignupRequestDTO;
import com.finalProject.ServiceBookingSystem.dto.UserDto;

public interface AuthService {

    UserDto signupClient(SignupRequestDTO signupRequestDTO);

    Boolean presentByEmail(String email);

    UserDto signupCompany(SignupRequestDTO signupRequestDTO);
}
