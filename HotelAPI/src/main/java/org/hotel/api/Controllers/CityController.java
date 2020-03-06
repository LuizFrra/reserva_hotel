package org.hotel.api.Controllers;

import org.hotel.api.EntitiesDAO.CityDAO;
import org.hotel.api.Models.City;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "city")
public class CityController {

    private CityDAO cityDAO;

    public CityController(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.POST)
    public Object add(@RequestBody City city, HttpServletResponse response) throws Exception {
        City result = null;
        try {
            result = cityDAO.AddCity(city);
        } catch (Exception e) {
            response.setStatus(409);
            return e.getMessage();
        }
        response.setStatus(201);
        return result;
    }

}
