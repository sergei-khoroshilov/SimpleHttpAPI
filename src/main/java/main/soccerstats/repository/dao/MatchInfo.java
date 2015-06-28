package main.soccerstats.repository.dao;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "MATCH_INFO")
public class MatchInfo {
    private int id;
    private Calendar matchDate;
    private String homeCommand;
    private String guestCommand;
    private String score;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "MATCH_DATE")
    public Calendar getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Calendar matchDate) {
        this.matchDate = matchDate;
    }

    @Column(name = "HOME_COMMAND")
    public String getHomeCommand() {
        return homeCommand;
    }

    public void setHomeCommand(String homeCommand) {
        this.homeCommand = homeCommand;
    }

    @Column(name = "GUEST_COMMAND")
    public String getGuestCommand() {
        return guestCommand;
    }

    public void setGuestCommand(String guestCommand) {
        this.guestCommand = guestCommand;
    }

    @Column(name = "SCORE")
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
