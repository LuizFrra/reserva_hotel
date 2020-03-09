package org.hotel.api.Models;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class BookRoom {
    
    private int Id;

    private int HotelRoomId;
    
    private EMonth Month;

    public BookRoom(int Id, int HotelRoomId, EMonth Month){
        this.Id = Id;
        this.HotelRoomId = HotelRoomId;
        this.Month = Month;
    }

    public int getId() {
        return this.Id;
    }

    public int getHotelRoomId() { return this.HotelRoomId; }

    public EMonth getMonth() { return Month; }

}