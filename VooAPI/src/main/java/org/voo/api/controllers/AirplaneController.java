package org.voo.api.controllers;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.voo.api.dto.AirPlaneDTO;
import org.voo.api.models.Airplane;
import org.voo.api.models.City;
import org.voo.api.models.FlyCompany;
import org.voo.api.repositories.AirplaneRepository;
import org.voo.api.repositories.CityRepository;
import org.voo.api.repositories.FlyCompanyRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping(value = "airplane")
@CrossOrigin(origins = {"*"})
public class AirplaneController {

    @Autowired
    @Setter
    private AirplaneRepository airplaneRepository;

    @Autowired
    @Setter
    private CityRepository cityRepository;

    @Autowired
    @Setter
    private FlyCompanyRepository flyCompanyRepository;

    @RequestMapping("city/{cityId}")
    public Object GetAirplanesByCity(@PathVariable(name = "cityId")int cityId, HttpServletResponse response) {
        return airplaneRepository.findByCityId((long)cityId);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object AddAirplane(@RequestBody AirPlaneDTO airPlaneDTO, HttpServletResponse response) {
        Optional<City> city = cityRepository.findById(airPlaneDTO.cityId);
        Optional<FlyCompany> flyCompany = flyCompanyRepository.findById(airPlaneDTO.companyId);

        if(city.isEmpty() || flyCompany.isEmpty()) {
            response.setStatus(400);
            return "Dados Incorretos";
        }

        Optional<Airplane> airplaneO = airplaneRepository.findById(airPlaneDTO.airplaneId);
        if(airplaneO.isPresent()) {
            response.setStatus(409);
            return "Entidade Já Existe";
        }

        Airplane airplane =  airplaneRepository.save(new Airplane(airPlaneDTO.airplaneId, airPlaneDTO.seat,
                                                                flyCompany.get(), city.get()));
        response.setStatus(201);
        return airplane;
    }
}
