package com.example.mikedrop;

public class Restaurants {

    private final String name;
    private final String address;

    public Restaurants(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }

    @Override
    public String toString(){
        return (getName() +"\n" + getAddress());
    }
}
