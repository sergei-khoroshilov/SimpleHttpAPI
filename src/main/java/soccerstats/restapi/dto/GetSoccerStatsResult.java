package soccerstats.restapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shenry on 27.06.2015.
 */
public class GetSoccerStatsResult extends MatchInfo {
    private int id;

    public GetSoccerStatsResult() {
    }

    public GetSoccerStatsResult(soccerstats.dal.matchinfo.MatchInfo matchInfo) {
        super(matchInfo);

        id = matchInfo.getId();
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
