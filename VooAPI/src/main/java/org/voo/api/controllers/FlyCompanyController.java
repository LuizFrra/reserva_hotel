package org.voo.api.controllers;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.voo.api.dto.FlyCompanyDTO;
import org.voo.api.models.FlyCompany;
import org.voo.api.repositories.FlyCompanyRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping(value = "flycompany")
@CrossOrigin(origins = {"*"})
public class FlyCompanyController {

    @Setter
    @Autowired
    private FlyCompanyRepository flyCompanyRepository;

    @RequestMapping(value = "{id}")
    public Object GetCompanyById(@PathVariable(name = "id")long id, HttpServletResponse response) {
        Optional<FlyCompany> flyCompany = flyCompanyRepository.findById(id);

        if(flyCompany.isEmpty()) {
            response.setStatus(204);
            return "null";
        }

        return flyCompany.get();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object AddCompany(@RequestBody FlyCompanyDTO flyCompanyDTO, HttpServletResponse response) {

        Optional<FlyCompany> flyCompanyO = flyCompanyRepository.findByName(flyCompanyDTO.name);
        if(flyCompanyO.isPresent()) {
            response.setStatus(409);
            return "Entidade já Existe.";
        }

        FlyCompany flyCompany = flyCompanyRepository.save(new FlyCompany((long)0, flyCompanyDTO.name));
        response.setStatus(201);
        return flyCompany;
    }

}
