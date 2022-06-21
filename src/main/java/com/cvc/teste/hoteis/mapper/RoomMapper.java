package com.cvc.teste.hoteis.mapper;

import org.springframework.stereotype.Component;

import com.cvc.teste.hoteis.dto.RoomDTO;
import com.cvc.teste.hoteis.entity.Room;

@Component
public class RoomMapper {
    public RoomDTO roomToRoomDTO(Room room) {
        RoomDTO roomDTO = RoomDTO.builder()
        .id(room.getRoomID()).build();
        return roomDTO;
    }
}
