package com.cvc.teste.hoteis.entity;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    private Long id;
    private String name;
    private Long cityCode;
    private String cityName;
    private Set<Room> rooms;
}
