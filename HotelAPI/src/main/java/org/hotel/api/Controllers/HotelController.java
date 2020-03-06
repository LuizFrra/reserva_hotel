package org.hotel.api.Controllers;

import org.hotel.api.EntitiesDAO.HotelDAO;
import org.hotel.api.EntitiesDAO.HotelRoomDAO;
import org.hotel.api.Models.Hotel;
import org.hotel.api.Models.HotelRoom;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "hotel")
public class HotelController {

    private HotelDAO hotelDAO;
    private HotelRoomDAO hotelRoomDAO;
    public HotelController(HotelDAO hotelDAO, HotelRoomDAO hotelRoomDAO) {
        this.hotelRoomDAO = hotelRoomDAO;
        this.hotelDAO = hotelDAO;
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.POST)
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

    @RequestMapping(value = {"bycity/{cityId}"}, method = RequestMethod.GET)
    public Object GetHotelByCity(@PathVariable(name = "cityId") int cityId, HttpServletResponse response) throws Exception {
        List<Hotel> hotels = null;

        try {
            hotels = hotelDAO.GetAllHotelsFromCity(cityId);
        } catch (Exception e) {
            response.setStatus(500);
            return "Error While Processing Request.";
        }

        if (hotels.isEmpty()) {
            response.setStatus(204);
            return null;
        }
        return hotels;
    }

    @RequestMapping(value = "addroom", method = RequestMethod.POST)
    public Object AddRoom(@RequestBody HotelRoom hotelRoom, HttpServletResponse response) throws Exception {
        HotelRoom result = null;
        try {
            result = hotelRoomDAO.AddHotelRoom(hotelRoom);
        } catch (Exception e) {
            response.setStatus(400);
            return "Hotel Doesn't Exist";
        }
        response.setStatus(201);
        return result;
    }

}
