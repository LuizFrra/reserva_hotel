package org.voo.api.controllers;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.voo.api.dto.FlightBookDTO;
import org.voo.api.models.Airplane;
import org.voo.api.repositories.AirplaneRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping(value = "flightbook")
@CrossOrigin(origins = {"*"}, methods = { RequestMethod.GET, RequestMethod.POST })
public class FlightBookController {

    @Autowired
    @Setter
    private AirplaneRepository airplaneRepository;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object AddFlightBook(@RequestBody FlightBookDTO flightBookDTO, HttpServletResponse response) {
        Optional<Airplane> airplaneO = airplaneRepository.findById(flightBookDTO.airplaneId);
        if(airplaneO.isEmpty()){
            response.setStatus(400);
            return "Dados Incorretos";
        }

        Airplane airplane = airplaneO.get();
        if(airplane.bookFlight(flightBookDTO.month)){
            airplane = airplaneRepository.save(airplane);
            response.setStatus(201);
            return airplane;
        }
        response.setStatus(400);
        return "Reserva Máxima Atingida para o voo.";
    }
}
