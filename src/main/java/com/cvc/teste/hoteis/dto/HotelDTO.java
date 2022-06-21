package com.cvc.teste.hoteis.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {
    
    private Long id;
    private String city;
    private String name;
    private Set<RoomDTO> rooms;
    
}
