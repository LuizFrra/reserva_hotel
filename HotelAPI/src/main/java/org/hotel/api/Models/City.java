package org.hotel.api.Models;

public class City {
    private int id;

    private String name;

    public City(int id, String name) throws Exception {

        if(name.length() > 255) {
            throw  new Exception("Name Cant Be Large than 255.");
        }
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
