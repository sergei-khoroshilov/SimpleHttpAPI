package main.soccerstats.restapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddSoccerStatsResult {
    public int id;

    public AddSoccerStatsResult() {
    }

    public AddSoccerStatsResult(int id) {
        this.id = id;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
