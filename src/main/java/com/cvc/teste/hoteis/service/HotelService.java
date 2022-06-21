package com.cvc.teste.hoteis.service;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cvc.teste.hoteis.dto.CategoryDTO;
import com.cvc.teste.hoteis.dto.HotelDTO;
import com.cvc.teste.hoteis.dto.PriceDetailDTO;
import com.cvc.teste.hoteis.dto.RoomDTO;
import com.cvc.teste.hoteis.entity.Hotel;
import com.cvc.teste.hoteis.entity.Room;
import com.cvc.teste.hoteis.error.exception.DataNotFoundException;
import com.cvc.teste.hoteis.form.HotelForm;
import com.cvc.teste.hoteis.mapper.CategoryMapper;
import com.cvc.teste.hoteis.mapper.HotelMapper;
import com.cvc.teste.hoteis.mapper.PriceDetailMapper;
import com.cvc.teste.hoteis.mapper.RoomMapper;




@Service
public class HotelService {

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private PriceDetailMapper priceDetailMapper;

    /**
     * Function that returns a List of {@link HotelDTO}
     * @param form
     * @return a {@link Set} of {@link HotelDTO}
     */
    public Set<HotelDTO> listByCityId(@Valid HotelForm form) {
        validateDataForm(form);
        Date firstDate = form.getCheckInDate();
        Date secondDate = form.getCheckOutDate();

        int numberOfDays = this.diffOfDays(firstDate, secondDate);
        
        String url = getUrl(form.getCityId(), form.getHotelId());
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Hotel>> entity = restTemplate.exchange(url, HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
        
        if(entity.getBody().size() < 1) throw new DataNotFoundException("Não foram encontrados registros para esta busca");
        
        return this.searchResult(entity.getBody(),numberOfDays, form.getNumberOfAdults(), form.getNumberOfChilds() );
       
    }

    /**
     * Function that create and returns a collection of {@link HotelDTO}
     * @param list
     * @param numberOfDays
     * @param adults
     * @param childs
     * @return a {@link Set} of {@link HotelDTO}
     */
    public Set<HotelDTO> searchResult(List<Hotel> list, Integer numberOfDays, Integer adults, Integer childs) {

        Set<HotelDTO> resultList = new HashSet<>();

         
        for (Hotel hotel : list) {
            HotelDTO hotelDTO = hotelMapper.hotelToHotelDTO(hotel);
            Set<Room> rooms = hotel.getRooms();
            Set<RoomDTO> roomsList = new HashSet<>();
            for(Room room : rooms){                
                RoomDTO roomDTO = roomMapper.roomToRoomDTO(room);
                CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(room.getCategoryName());
                PriceDetailDTO priceDetailDTO = priceDetailMapper.pricedetailToPriceDetailDTO(room.getPrice());
                roomDTO.setCategory(categoryDTO);
                roomDTO.setTotalPrice(this.calculateTotalPrice(room, numberOfDays, adults, childs));
                roomDTO.setPriceDetail(priceDetailDTO);
                
                roomsList.add(roomDTO);
    
            }            
            hotelDTO.setRooms(roomsList);
            resultList.add(hotelDTO);
        }
        return resultList;
    }

    /**
     * Function that return the dyas of difference between two dates
     * @param firstDate
     * @param secondDate
     * @return number of days
     */
    public int diffOfDays(Date firstDate, Date secondDate) {
		long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return Math.toIntExact(diff);
	}   

    /**
     * Function that calculate total price of an search of rooms
     * the returned value consist in the sum of total price for adults, total price for childs and the commission
     * @param room
     * @param numberOfDays
     * @param adults
     * @param childs
     * @return a {@link BigDecimal} of the calculated price
     */
    public BigDecimal calculateTotalPrice(Room room, Integer numberOfDays, Integer adults, Integer childs){
        BigDecimal totalAdult = (room.getPrice().getAdult().multiply(new BigDecimal(numberOfDays.toString()))).multiply(new BigDecimal(adults));
        BigDecimal totalChild = (room.getPrice().getChild().multiply(new BigDecimal(numberOfDays.toString()))).multiply(new BigDecimal(childs));
        BigDecimal comission = new BigDecimal((totalAdult.add(totalChild)).doubleValue() / 0.7);
        BigDecimal totalPrice = (totalAdult.add(totalChild)).add(comission);

        return totalPrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * Function to validate {@link HotelForm}
     * @param form
     */
    public void validateDataForm(HotelForm form) {
        if(form.getCheckInDate().after(form.getCheckOutDate())){
            throw new ValidationException("A data de checkin deve ser após a data de checkout");
        }
        if(form.getNumberOfAdults() < 0 || form.getNumberOfChilds() < 0){
            throw new ValidationException("Não podem haver quantidades negativas de hospedes");
        }
        if(form.getNumberOfAdults() == 0 && form.getNumberOfChilds() == 0){
            throw new ValidationException("Deve haver pelo menos 1 hospede para fazer a verificação");
        }
    }

    public String getUrl(Long cityId, Long hotelId){
       
        if(hotelId != null){
            return "https://cvcbackendhotel.herokuapp.com/hotels/" + hotelId.toString();
        }
        
        return "https://cvcbackendhotel.herokuapp.com/hotels/avail/" + cityId.toString();
        
    }
  
}
