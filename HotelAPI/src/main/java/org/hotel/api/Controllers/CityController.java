package org.hotel.api.Controllers;

import org.hotel.api.EntitiesDAO.CityDAO;
import org.hotel.api.Models.City;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "city")
//@CrossOrigin(origins = {"*"})
public class CityController {

    private CityDAO cityDAO;

    public CityController(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.POST)
    public Object Add(@RequestBody City city, HttpServletResponse response) throws Exception {
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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object GetAllCities(HttpServletResponse response) {

        List<City> cities = null;

        try {
            cities = cityDAO.GetAllCities();
        } catch (Exception e) {
            response.setStatus(500);
            return e.getMessage();
        }

        if(cities.isEmpty()) {
            response.setStatus(204);
            return null;
        }

        response.setStatus(200);
        return cities;
    };

}
