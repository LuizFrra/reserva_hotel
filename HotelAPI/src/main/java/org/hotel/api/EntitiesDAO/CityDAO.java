package org.hotel.api.EntitiesDAO;

import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import org.hotel.api.Models.City;
import org.hotel.api.Models.Hotel;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CityDAO {

    private JdbcTemplate jdbcTemplate;

    public CityDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public City AddCity(City city) throws Exception {
        City citySearch = CityExistByName(city.getName());

        if(citySearch != null) {
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

    public City CityExistById(int id) throws Exception {
        String query = "SELECT * FROM tbl_cities WHERE id = ?;";
        City city = null;
        try {
            city = jdbcTemplate.queryForObject(query, new Object[]{ id }, new RowMapper<City>() {
                @Override
                public City mapRow(ResultSet rs, int rowNum) throws SQLException {
                    City cityIntern = null;

                    try {
                        cityIntern = new City(rs.getInt("id"), rs.getString("name"));
                    } catch (Exception e) {
                        throw new SQLException("Error While Looking for City.");
                    }

                    return cityIntern;
                }
            });

        } catch (EmptyResultDataAccessException e ) {
            city = null;
        }
        return city;
    }

    public City CityExistByName(String name) throws Exception {
        String query = "SELECT * FROM tbl_cities WHERE name = ?;";
        City city = null;
        try {
            city = jdbcTemplate.queryForObject(query, new Object[]{ name }, new RowMapper<City>() {
                @Override
                public City mapRow(ResultSet rs, int rowNum) throws SQLException {
                    City cityIntern = null;

                    try {
                        cityIntern = new City(rs.getInt("id"), rs.getString("name"));
                    } catch (Exception e) {
                        throw new SQLException("Error While Looking for City.");
                    }
                    return cityIntern;
                }
            });

        } catch (EmptyResultDataAccessException e ) {
            city = null;
        }
        return city;
    }

    public List<City> GetAllCities() throws Exception {

        List<Map<String,Object>> rows =  jdbcTemplate.queryForList("SELECT * FROM tbl_cities;");
        List<City> cities = new ArrayList<City>();

        try {
            for(Map<String, Object> row : rows) {
                cities.add(new City((int)row.get("id"), (String)row.get("name")));
            }
        } catch (Exception e) {
            throw new Exception("Problem while get Cities.");
        }

        return cities;
    }

}
