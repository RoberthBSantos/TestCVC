package com.cvc.teste.hoteis.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cvc.teste.hoteis.dto.HotelDTO;
import com.cvc.teste.hoteis.form.HotelForm;
import com.cvc.teste.hoteis.service.HotelService;


@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping
    @Cacheable("get")
    public ResponseEntity<Set<HotelDTO>> get(@Valid @RequestBody HotelForm form ){
       try {
        Set<HotelDTO> list = hotelService.listByCityId(form);
        return ResponseEntity.ok(list);
       } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
       }
        
        
    }
}
