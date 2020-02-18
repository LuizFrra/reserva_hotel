package org.voo.api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.web.bind.annotation.*;
import org.voo.api.models.Person;
import org.voo.api.repository.PersonDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
public class HomeController {

    private PersonDAO personDAO;

    public HomeController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    // Exemplo De Insert
    @RequestMapping(
            value = {"Index", "index"},
            method = RequestMethod.GET,
            params = {"id", "name"})
     public String index( @RequestParam("id") int id, @RequestParam("name") String name) {
        Person person = new Person(id, name);
        personDAO.savePersonWithPreparedStatement(person);
        return Integer.toString(id) + name;
     }

     // Enviar no Body
     @RequestMapping(value = {"addPerson"}, method = RequestMethod.POST)
     public String addPerson(@RequestBody Person person) {
        personDAO.savePersonWithPreparedStatement(person);
        return  person.getName() + Integer.toString((person.getId()));
     }

     // Enviar como x-www-form-urlencoded
     @RequestMapping(value = "person")
    public String personAdd(@ModelAttribute("person") Person person) {
        return person.getName() + Integer.toString(person.getId());
    }
}
