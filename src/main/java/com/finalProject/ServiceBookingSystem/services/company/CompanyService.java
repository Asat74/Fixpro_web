package com.finalProject.ServiceBookingSystem.services.company;

import com.finalProject.ServiceBookingSystem.dto.AdDTO;

import java.io.IOException;

public interface CompanyService {

    boolean postAd(Long userId, AdDTO adDTO) throws IOException;
}
