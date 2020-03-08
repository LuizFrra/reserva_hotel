package org.hotel.api.EntitiesDAO;

import org.hotel.api.Models.BookRoom;
import org.hotel.api.Models.HotelRoom;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;

@Component
public class BookRoomDAO {

    private JdbcTemplate jdbcTemplate;
    private HotelRoomDAO hotelRoomDAO;

    public BookRoomDAO(JdbcTemplate jdbcTemplate, HotelRoomDAO hotelRoomDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.hotelRoomDAO = hotelRoomDAO;
    }

    public BookRoom AddBook(BookRoom bookRoom) throws Exception {

        if(!hotelRoomDAO.hotelRoomExist(bookRoom.getHotelRoomId()))
            throw new Exception("Hotel Room Doesn't Exist.");

        if(RoomIsBooked(bookRoom))
            throw new Exception("Room Already Booked");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO tbl_bookroom(hotelsroomid, month) VALUES " +
                    "(? , ?);", new String[] { "id" });
            ps.setInt(1, bookRoom.getHotelRoomId());
            ps.setInt(2, bookRoom.getMonth().ordinal());
            return ps;
        }, keyHolder);

        return new BookRoom((int)keyHolder.getKey(), bookRoom.getHotelRoomId(), bookRoom.getMonth());
    }

    public boolean RoomIsBooked(BookRoom bookRoom) {
        String query = "SELECT COUNT(*) FROM tbl_bookroom WHERE hotelsroomid = ? AND month = ?;";
        Integer count = jdbcTemplate.queryForObject(query, new Object[] { bookRoom.getHotelRoomId(),
                        bookRoom.getMonth().ordinal()},
                Integer.class);

        if(count == null)
            return false;

        return count == 1;
    }

}
