package org.voo.api.models;

public class Person {

    private int Id;

    private String Name;

    public Person(int Id, String Name) {
        this.Id = Id;
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }

    public int getId() {
        return Id;
    }

}
