package Hotels;

import org.jsoup.nodes.Element;

import java.util.ArrayList;

public class Hotel {
    String name;
    ArrayList<String> rating;
    String location;
    ArrayList<String> services;
    ArrayList<String> reviews;


    public Hotel(String name, ArrayList<String> rating,String location,ArrayList<String> services,ArrayList<String> reviews) {
        this.name = name;
        this.rating = rating;
        this.location = location;
        this.services = services;
        this.reviews = reviews;
    }
}
