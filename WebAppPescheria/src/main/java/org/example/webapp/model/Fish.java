package org.example.webapp.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Fish {
    protected String name;
    protected String fisherman;
    protected List<FishingZone> fishingZones;

    public Fish(String name, String fisherman) {
        this.name = name;
        this.fisherman = fisherman;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFisherman() {
        return fisherman;
    }

    public void setFisherman(String fisherman) {
        this.fisherman = fisherman;
    }


    public List<FishingZone> getFishingZones() {
        return fishingZones;
    }

    public void setFishingZones(List<FishingZone> fishingZones) {
        this.fishingZones = fishingZones;
    }
}
