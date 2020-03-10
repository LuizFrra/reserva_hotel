package org.hotel.api.EntitiesDAO;

import lombok.Setter;
import org.hotel.api.Models.BookRoom;
import org.hotel.api.Models.EMonth;
import org.hotel.api.Models.HotelRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.handler.ExceptionHandlingWebHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class HotelRoomDAO {
    private JdbcTemplate jdbcTemplate;

    @Setter
    @Autowired
    private HotelDAO hotelDAO;

    @Setter
    @Autowired
    private BookRoomDAO bookRoomDAO;

    public HotelRoomDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

    public boolean HotelRoomExist(int hotelRoomId) {
        String query =  "SELECT COUNT(*) FROM tbl_hotelsroom WHERE id = ?;";
        Integer result = jdbcTemplate.queryForObject(query, new Object[] { hotelRoomId }, Integer.class);
        return result == 1;
    }

    public HotelRoom HotelRoomById(int hotelRoomId) throws Exception {
        String query = "SELECT * FROM tbl_hotelsroom WHERE id = ?;";
        HotelRoom hotelRoom = null;

        if(!HotelRoomExist(hotelRoomId))
            throw new Exception("Hotel Room Does't Exist.");

        try {
            hotelRoom = jdbcTemplate.queryForObject(query, new Object[]{hotelRoomId}, new RowMapper<HotelRoom>() {
                @Override
                public HotelRoom mapRow(ResultSet rs, int rowNum) throws SQLException {
                    HotelRoom hotelRoomInner = new HotelRoom(hotelRoomId, rs.getInt("hotel_id"), rs.getBoolean("disabled"));
                    return hotelRoomInner;
                }
            });
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }

        List<BookRoom> booksRoom =  bookRoomDAO.BooksFromRoom(hotelRoomId);
        for(BookRoom bookRoom : booksRoom) {
            hotelRoom.BookARoom(bookRoom.getId(), bookRoom.getMonth());
        }

        return hotelRoom;
    }

}
