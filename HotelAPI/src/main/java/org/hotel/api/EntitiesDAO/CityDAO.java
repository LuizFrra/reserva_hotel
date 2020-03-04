package org.hotel.api.EntitiesDAO;

import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import org.hotel.api.Models.City;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class CityDAO {

    private JdbcTemplate jdbcTemplate;

    public CityDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public City addCity(City city) throws Exception {
        String query = "SELECT COUNT(*) FROM tbl_cities WHERE name = ?";
        int numberOfCitiesMatchName = jdbcTemplate.queryForObject(query, new Object[] {city.getName()}, Integer.class);

        if(numberOfCitiesMatchName > 0) {
            throw new Exception("City " + city.getName() + " Already Exist.");
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO tbl_cities(name) VALUES(?)",
                    new String[] {"id"});
            ps.setString(1, city.getName());
            return ps;
        }, keyHolder);

        return new City((int)keyHolder.getKey(), city.getName());
    }
}
