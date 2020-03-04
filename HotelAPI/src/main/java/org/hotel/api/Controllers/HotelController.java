package org.hotel.api.Controllers;

import org.hotel.api.EntitiesDAO.HotelDAO;
import org.hotel.api.Models.Hotel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class HotelController {

    private HotelDAO hotelDAO;

    public HotelController(HotelDAO hotelDAO) {
        this.hotelDAO = hotelDAO;
    }

    @RequestMapping(value = {"hotel/add"}, method = RequestMethod.POST)
    public Object AddHotel(@RequestBody Hotel hotel, HttpServletResponse response) throws Exception {
        Hotel result = null;
        try {
            result = hotelDAO.AddHotel(hotel);
        } catch (Exception e) {
            response.setStatus(409);
            return e.getMessage();
        }
        response.setStatus(201);
        return result;
    }
}
