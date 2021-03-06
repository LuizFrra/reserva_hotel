package org.hotel.api.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.bitbucket.dollar.Dollar.$;

public class HotelRoom {

    private int Id;

    private int HotelId;

    private boolean Disabled;

    private List<BookRoom> bookRoomList;

    public HotelRoom(int Id, int HotelId, boolean Disabled) {
        this.Id = Id;
        this.HotelId = HotelId;
        this.Disabled = Disabled;
        bookRoomList = new ArrayList<BookRoom>();
    }

    public void disableRoom() {
        this.Disabled = true;
    }

    public void ActiveRoom() {
        this.Disabled = false;
    }

    public boolean BookARoom(int bookRoomId, EMonth month) {
        boolean booked = false;
        Iterator<BookRoom> bookRoomIterator = bookRoomList.iterator();

        while (bookRoomIterator.hasNext()) {
            BookRoom bookRoom = bookRoomIterator.next();
            if(bookRoom.getMonth() == month)
                booked = true;
        }

        if (!booked) {
            BookRoom bookRoom = new BookRoom(bookRoomId, this.Id, month);
            return bookRoomList.add(bookRoom);
        }

        return false;
    }

    public List<Integer> getAvailableMonths() {
        List<Integer> availableMonths = $(0, 12).toList();

        for (BookRoom bookRoom : bookRoomList) {
            availableMonths.remove((Integer)bookRoom.getMonth().ordinal());
        }
        return availableMonths;
    }

    public int getId() { return this.Id; }

    public int getHotelId() { return this.HotelId; }

    public boolean isRoomDisabled() { return this.Disabled; }
}