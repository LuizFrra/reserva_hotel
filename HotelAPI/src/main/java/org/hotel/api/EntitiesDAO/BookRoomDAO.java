package org.hotel.api.EntitiesDAO;

import lombok.extern.slf4j.Slf4j;
import org.hotel.api.Models.BookRoom;
import org.hotel.api.Models.EMonth;
import org.hotel.api.Models.HotelRoom;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                    "(? , ?);", new String[]{ "id" });
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

    public List<BookRoom> BooksFromRoom(int roomId) {
        String query = "SELECT * FROM tbl_bookroom WHERE hotelsroomid = ?;";

        List<BookRoom> booksRoom = new ArrayList<BookRoom>();
        List<Map<String, Object>> rows =  jdbcTemplate.queryForList(query, roomId);

        for(Map row : rows) {

            BookRoom bookRoom = new BookRoom((int)row.get("id"), roomId, EMonth.ValueOf((int)row.get("month")));
            booksRoom.add(bookRoom);
        }

        return booksRoom;
    }

}
