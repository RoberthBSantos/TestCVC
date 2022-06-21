package com.cvc.teste.hoteis.dto;
import java.math.BigDecimal;

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
public class PriceDetailDTO {
    private BigDecimal pricePerDayAdult;
    private BigDecimal pricePerDayChild;
}
