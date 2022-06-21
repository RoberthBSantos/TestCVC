package com.cvc.teste.hoteis.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

import com.cvc.teste.hoteis.dto.PriceDetailDTO;
import com.cvc.teste.hoteis.entity.Price;

@Component
public class PriceDetailMapper {
    public PriceDetailDTO pricedetailToPriceDetailDTO(Price price) {
        BigDecimal priceAdult = price.getAdult().add(new BigDecimal(price.getAdult().doubleValue() / 0.7));
        BigDecimal priceChild = price.getChild().add(new BigDecimal(price.getChild().doubleValue() / 0.7));
        PriceDetailDTO priceDetailDTO = PriceDetailDTO.builder()
        .pricePerDayAdult(priceAdult.setScale(2, RoundingMode.HALF_EVEN))
        .pricePerDayChild(priceChild.setScale(2, RoundingMode.HALF_EVEN))
        .build();
        return priceDetailDTO;
    }
}
