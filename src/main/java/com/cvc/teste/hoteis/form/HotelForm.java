package com.cvc.teste.hoteis.form;

import java.util.Date;


import javax.validation.constraints.NotNull;

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
public class HotelForm {

    @NotNull(message = "O id da cidade não pode estar nulo." )
    private Long cityId;

    @NotNull(message = "A data de checkin não pode ser nula.")
    private Date checkInDate;

    @NotNull(message = "A data de checkout não pode ser nula.")
    private Date checkOutDate;

    @NotNull(message = "O numero de adultos não pode ser nulo")
    private Integer numberOfAdults;

    @NotNull(message = "O numero de crianças não pode ser nulo")
    private Integer numberOfChilds;

    private Long hotelId;
}
