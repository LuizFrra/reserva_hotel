package org.hotel.api.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Hotel {

    private int Id;

    private String Name;

    private String City;

    private List<HotelRoom> hotelRoomList;

    public Hotel(int Id, String Name, String City) throws Exception {
        this.Id = Id;

        if(Name.length() > 255)
            throw new Exception("Name Out Of Length");

        if(City.length() > 255)
            throw new Exception("City Out Of Length");

        this.Name =  Name;
        this.City = City;
        hotelRoomList = new ArrayList<HotelRoom>();
    }

    public boolean addRoom(HotelRoom hotelRoom) {
        return hotelRoomList.add(hotelRoom);
    }

    public boolean removeRoom(HotelRoom hotelRoom) {
        return hotelRoomList.remove(hotelRoom);
    }

    public HotelRoom getHotelRoomById(int Id) {
        Iterator<HotelRoom> hotelRoomIterator = hotelRoomList.iterator();

        while (hotelRoomIterator.hasNext()) {

            HotelRoom hotelRoom = hotelRoomIterator.next();

            if(hotelRoom.getId() == Id)
                return hotelRoom;
        }

        return null;
    }

    public boolean removeHotelRoomById(int Id) {
        Iterator<HotelRoom> hotelRoomIterator = hotelRoomList.iterator();

        while (hotelRoomIterator.hasNext()) {

            HotelRoom hotelRoom = hotelRoomIterator.next();

            if(hotelRoom.getId() == Id)
            {
                hotelRoomIterator.remove();
                return true;
            }
        }

        return false;
    }

    public int getId() { return this.Id; }

    public String getName() { return this.Name; }

    public String getCity() { return this.City; }
}