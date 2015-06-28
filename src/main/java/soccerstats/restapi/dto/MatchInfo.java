package soccerstats.restapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;

/**
 * Created by shenry on 27.06.2015.
 */
    public class MatchInfo {
        @JsonProperty("date")
        private Calendar matchDate;

        @JsonProperty("home")
        private String homeCommand;

        @JsonProperty("guest")
        private String guestCommand;

        @JsonProperty("score")
        private String score;


    public MatchInfo() {
    }

    public MatchInfo(soccerstats.repository.dao.MatchInfo daoMatchInfo) {
        matchDate = daoMatchInfo.getMatchDate();
        homeCommand = daoMatchInfo.getHomeCommand();
        guestCommand = daoMatchInfo.getGuestCommand();
        score = daoMatchInfo.getScore();
    }

    public Calendar getMatchDate() {
            return matchDate;
        }

        public void setMatchDate(Calendar matchDate) {
            this.matchDate = matchDate;
        }

        public String getHomeCommand() {
            return homeCommand;
        }

        public void setHomeCommand(String homeCommand) {
            this.homeCommand = homeCommand;
        }

        public String getGuestCommand() {
            return guestCommand;
        }

        public void setGuestCommand(String guestCommand) {
            this.guestCommand = guestCommand;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }