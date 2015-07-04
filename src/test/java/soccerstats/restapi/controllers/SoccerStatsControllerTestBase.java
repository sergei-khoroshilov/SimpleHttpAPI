package soccerstats.restapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import soccerstats.Application;
import soccerstats.dal.matchinfo.MatchInfoRepository;
import soccerstats.dal.matchinfo.MatchInfo;
import soccerstats.restapi.dto.AddSoccerStatsRequest;

import java.nio.charset.Charset;
import java.util.GregorianCalendar;

@SpringApplicationConfiguration(classes = {Application.class})
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
public class SoccerStatsControllerTestBase {

    protected static final String API_PATH = "/soccer/info/";

    protected static final MediaType CONTENT_JSON = new MediaType(MediaType.APPLICATION_JSON.getType(),
                                                                  MediaType.APPLICATION_JSON.getSubtype(),
                                                                  Charset.forName("utf8"));

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected MatchInfoRepository matchInfoRepository;

    protected MatchInfo matchInfo;

    public void setup() throws Exception {
        matchInfoRepository.deleteAllInBatch();

        MatchInfo matchInfo = new MatchInfo();
        matchInfo.setMatchDate(new GregorianCalendar(2015, 5, 1));
        matchInfo.setHomeCommand("Arsenal");
        matchInfo.setGuestCommand("Liverpool");
        matchInfo.setScore("3-1");

        this.matchInfo = matchInfoRepository.save(matchInfo);
    }

    public AddSoccerStatsRequest getAddSoccerStatsRequest () {
        AddSoccerStatsRequest added = new AddSoccerStatsRequest();

        added.setMatchDate(new GregorianCalendar(2015, 7, 6));
        added.setHomeCommand("Barcelona");
        added.setGuestCommand("Real Madrid");
        added.setScore("2-2");

        return added;
    }

    public String getMatchUrl(int matchId) {
        return API_PATH + matchId;
    }
}
