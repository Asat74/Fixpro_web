package com.finalProject.ServiceBookingSystem.services.client;

import com.finalProject.ServiceBookingSystem.dto.AdDTO;
import com.finalProject.ServiceBookingSystem.dto.AdDetailsForClientDTO;
import com.finalProject.ServiceBookingSystem.dto.ReservationDTO;

import java.util.List;

public interface ClientService {

    List<AdDTO> getAllAds();

    List<AdDTO> searchAdByName(String name);

    boolean bookService(ReservationDTO reservationDTO);

    AdDetailsForClientDTO getAdDetailsByAdId(Long adId);

    List<ReservationDTO> getAllBookingsByUserId(Long userId);
}
