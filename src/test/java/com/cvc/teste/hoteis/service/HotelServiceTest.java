package com.cvc.teste.hoteis.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.ValidationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cvc.teste.hoteis.dto.HotelDTO;
import com.cvc.teste.hoteis.entity.Hotel;
import com.cvc.teste.hoteis.entity.Price;
import com.cvc.teste.hoteis.entity.Room;
import com.cvc.teste.hoteis.error.exception.DataNotFoundException;
import com.cvc.teste.hoteis.form.HotelForm;
import com.cvc.teste.hoteis.mapper.CategoryMapper;
import com.cvc.teste.hoteis.mapper.HotelMapper;
import com.cvc.teste.hoteis.mapper.PriceDetailMapper;
import com.cvc.teste.hoteis.mapper.RoomMapper;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Hotel Service")
public class HotelServiceTest {
    
    @InjectMocks
    private HotelService hotelService;

    @Mock
    private HotelMapper hotelMapper;

    @Mock
    private RoomMapper roomMapper;

    @Mock
    private PriceDetailMapper detailMapper;

    @Mock
    private CategoryMapper categoryMapper;

    @BeforeEach
    public void setUp() {
        BDDMockito.when(hotelMapper.hotelToHotelDTO(ArgumentMatchers.any(Hotel.class))).thenCallRealMethod();
        BDDMockito.when(roomMapper.roomToRoomDTO(ArgumentMatchers.any(Room.class))).thenCallRealMethod();
        BDDMockito.when(detailMapper.pricedetailToPriceDetailDTO(ArgumentMatchers.any(Price.class))).thenCallRealMethod();
        BDDMockito.when(categoryMapper.categoryToCategoryDTO(ArgumentMatchers.any(String.class))).thenCallRealMethod();
    }

    @Test
    public void listByCityId_ReturnsHotelDTOSet_Success() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        long cityId = 1032;
        Date checkInDate = formatter.parse("2022-06-25");
        Date checkOutDate = formatter.parse("2022-06-30");
        HotelForm hotelForm = HotelForm.builder().cityId(cityId).checkInDate(checkInDate)
        .checkOutDate(checkOutDate).numberOfAdults(1).numberOfChilds(0)
        .build();

        Set<HotelDTO> set = hotelService.listByCityId(hotelForm);
        List<HotelDTO> body = new ArrayList<>(set);
        Assertions.assertThat(body).isNotNull();
        Assertions.assertThat(body.get(0).getId()).isNotNull();
        Assertions.assertThat(body.get(0).getCity()).isNotNull();
        Assertions.assertThat(body.get(0).getName()).isNotNull();
        Assertions.assertThat(body.get(0).getRooms()).isNotNull();
    }

    @Test
    public void listByCityId_ReturnsHotelDTOSetOneIndex_Success() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        long cityId = 1032;
        long hotelId = 1;
        Date checkInDate = formatter.parse("2022-06-25");
        Date checkOutDate = formatter.parse("2022-06-30");
        HotelForm hotelForm = HotelForm.builder().cityId(cityId).checkInDate(checkInDate)
        .checkOutDate(checkOutDate).numberOfAdults(1).numberOfChilds(0)
        .hotelId(hotelId)
        .build();

        Set<HotelDTO> set = hotelService.listByCityId(hotelForm);
        List<HotelDTO> body = new ArrayList<>(set);
        Assertions.assertThat(body).isNotNull();
        Assertions.assertThat(set.size()).isEqualTo(1);
        Assertions.assertThat(body.get(0).getId()).isNotNull();
        Assertions.assertThat(body.get(0).getCity()).isNotNull();
        Assertions.assertThat(body.get(0).getName()).isNotNull();
        Assertions.assertThat(body.get(0).getRooms()).isNotNull();
    }

    @Test
    public void listByCityId_TryReturnSetHotelDTODateValidate_Fail() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        long cityId = 1032;
        long hotelId = 1;
        Date checkInDate = formatter.parse("2022-06-25");
        Date checkOutDate = formatter.parse("2022-06-22");
        HotelForm hotelForm = HotelForm.builder().cityId(cityId).checkInDate(checkInDate)
        .checkOutDate(checkOutDate).numberOfAdults(1).numberOfChilds(0)
        .hotelId(hotelId)
        .build();

        Assertions.assertThatExceptionOfType(ValidationException.class)
                        .isThrownBy(()-> hotelService.listByCityId(hotelForm));
    }

    /**
     * Test should throw a {@link DataNotFoundException} when hotelId not found results on broker api
     * @throws ParseException
     */
    @Test
    public void listByCityId_TryReturnSetHotelDTOByHotelId_Fail() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        long cityId = 1032;
        long hotelId = 2345678;
        Date checkInDate = formatter.parse("2022-06-25");
        Date checkOutDate = formatter.parse("2022-06-30");
        HotelForm hotelForm = HotelForm.builder().cityId(cityId).checkInDate(checkInDate)
        .checkOutDate(checkOutDate).numberOfAdults(1).numberOfChilds(0)
        .hotelId(hotelId)
        .build();

        Assertions.assertThatExceptionOfType(DataNotFoundException.class)
                        .isThrownBy(()-> hotelService.listByCityId(hotelForm));
    }
    
}
