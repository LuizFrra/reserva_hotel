package org.hotel.api.EntitiesDAO;

import org.hotel.api.Models.HotelRoom;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;

@Component
public class HotelRoomDAO {
    private JdbcTemplate jdbcTemplate;
    private HotelDAO hotelDAO;

    public HotelRoomDAO(JdbcTemplate jdbcTemplate, HotelDAO hotelDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.hotelDAO = hotelDAO;
    }

    public HotelRoom AddHotelRoom(HotelRoom hotelRoom) throws Exception {

        String query = "INSERT INTO tbl_hotelsroom(hotel_id, disabled) VALUES(? , ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        if(!hotelDAO.HotelExistById(hotelRoom.getHotelId()))
            throw new Exception("Hotel Doesn't Exist");

        jdbcTemplate.update(con ->{
            PreparedStatement ps =  con.prepareStatement(query, new String[]{ "id" });
            ps.setInt(1, hotelRoom.getHotelId());
            ps.setBoolean(2, false);
            return ps;
        }, keyHolder);

        return new HotelRoom((int)keyHolder.getKey(), hotelRoom.getHotelId(), false);
    }

    public boolean hotelRoomExist(int hotelRoomId) {
        String query =  "SELECT COUNT(*) FROM tbl_hotelsroom WHERE id = ?;";
        Integer result = jdbcTemplate.queryForObject(query, new Object[] { hotelRoomId }, Integer.class);
        return result == 1;
    }
}
