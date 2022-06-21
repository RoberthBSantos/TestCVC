package com.cvc.teste.hoteis.mapper;

import org.springframework.stereotype.Component;

import com.cvc.teste.hoteis.dto.HotelDTO;
import com.cvc.teste.hoteis.entity.Hotel;

@Component
public class HotelMapper {
    
    public HotelDTO hotelToHotelDTO(Hotel h1){
        HotelDTO hotelDTO = HotelDTO.builder()
        .city(h1.getCityName())
        .id(h1.getId())
        .name(h1.getName()).build();

        return hotelDTO;

    }
}
