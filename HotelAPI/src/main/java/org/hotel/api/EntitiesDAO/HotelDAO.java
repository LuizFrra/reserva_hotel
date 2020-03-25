package org.hotel.api.EntitiesDAO;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.hotel.api.Models.City;
import org.hotel.api.Models.Hotel;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HotelDAO {

    private JdbcTemplate jdbcTemplate;
    private CityDAO cityDAO;
    public HotelDAO(JdbcTemplate jdbcTemplate, CityDAO cityDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.cityDAO = cityDAO;
    }

    public Hotel AddHotel(Hotel hotel) throws Exception {
        City city = cityDAO.CityExistById(hotel.getCity());
        if(city == null) {
            throw new Exception("City Doesn't Exist.");
        }
        Hotel hotelLook = HotelExist(hotel.getName(), hotel.getCity());
        if(hotelLook != null) {
            throw new Exception("Hotel " + hotelLook.getName() + " From City " + hotelLook.getCity() +
                    " Already Exist");
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO tbl_hotels(name, city_id) VALUES(?, ?);",
                    new String[] {"id"});
            ps.setString(1, hotel.getName());
            ps.setInt(2, hotel.getCity());
            return ps;
        }, keyHolder);

        return new Hotel((int)keyHolder.getKey(), hotel.getName(), hotel.getCity());
    }

    public Hotel HotelExist(String Name, int CityId) {
        String query = "SELECT * FROM tbl_hotels WHERE name = ? AND city_id = ?;";
        Hotel hotel = null;
        try {
            hotel = jdbcTemplate.queryForObject(query, new Object[]{Name, CityId}, new RowMapper<Hotel>() {
                @Override
                public Hotel mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {
                        return new Hotel(rs.getInt("id"), rs.getString("name"),
                                rs.getInt("city_id"));
                    } catch (Exception e) {
                        throw new SQLException("Error While Looking for Hotel.");
                    }
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return hotel;
    }

    public boolean HotelExistById(int hotelId) {
        String query =  "SELECT COUNT(*) FROM tbl_hotels WHERE id = ?";
        Integer result = jdbcTemplate.queryForObject(query, new Object[]{ hotelId }, Integer.class);
        return result == 1;
    }

    public List<Hotel> GetAllHotelsFromCity(int cityId) throws Exception {

        String query = "SELECT * FROM tbl_hotels WHERE city_id = ?;";
        List<Map<String, Object>> rows =  jdbcTemplate.queryForList(query, cityId);
        List<Hotel> hotels = new ArrayList<>();

        for(Map row : rows) {
            int hotelId = (int)row.get("id");
            String hotelName = (String)row.get("name");
            Hotel hotel = new Hotel(hotelId, hotelName, cityId);
            hotels.add(hotel);
        }
        return hotels;
    }

    public Hotel GetHotelById(int id) {
        String query = "SELECT * FROM tbl_hotels WHERE id = ?;";
        Hotel hotel = jdbcTemplate.queryForObject(query, new Object[]{id}, new RowMapper<Hotel>() {
            @Override
            public Hotel mapRow(ResultSet rs, int rowNum) throws SQLException {
                try {
                    return new Hotel(rs.getInt("id"), rs.getString("name"),
                                                rs.getInt("city_id"));
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });

        return hotel;
    }
}
