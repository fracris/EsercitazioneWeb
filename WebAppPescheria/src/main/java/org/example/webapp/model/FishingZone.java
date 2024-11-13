package org.example.webapp.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FishingZone {

    protected String name;
    protected String country;
    protected List<Fish> fishes;

    public FishingZone(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public List<Fish> getFishes() {
        return fishes;
    }

    public void setFishes(List<Fish> fishes) {
        this.fishes = fishes;
    }
}
