package soccerstats.restapi.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import soccerstats.Application;
import soccerstats.repository.MatchInfoRepository;
import soccerstats.repository.dao.MatchInfo;
import soccerstats.restapi.dto.AddSoccerStatsRequest;
import soccerstats.restapi.dto.AddSoccerStatsResult;
import utils.JsonUtils;

import java.nio.charset.Charset;
import java.util.GregorianCalendar;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class SoccerStatsControllerTest {

    private final String apiPath = "/soccer/info/";

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
                                                        MediaType.APPLICATION_JSON.getSubtype(),
                                                        Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MatchInfoRepository matchInfoRepository;

    private MatchInfo matchInfo;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                 .build();

        matchInfoRepository.deleteAllInBatch();

        MatchInfo matchInfo = new MatchInfo();
        matchInfo.setMatchDate(new GregorianCalendar(2015, 5, 1));
        matchInfo.setHomeCommand("Arsenal");
        matchInfo.setGuestCommand("Liverpool");
        matchInfo.setScore("3-1");
        this.matchInfo = matchInfoRepository.save(matchInfo);
    }

    private int getNotFoundMatchInfoId() {
        return matchInfo.getId() + 1;
    }

    @Test
    public void getMatchInfo_NotFound() throws Exception {
        mockMvc.perform(get(apiPath + getNotFoundMatchInfoId()))
               .andExpect(status().isNotFound());
    }

    @Test
    public void getMatchInfo_Success() throws Exception {
        mockMvc.perform(get(apiPath + matchInfo.getId()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$.*", hasSize(5)))
               .andExpect(jsonPath("$.id", is(matchInfo.getId())))
               .andExpect(jsonPath("$.date", is(matchInfo.getMatchDate().getTimeInMillis())))
               .andExpect(jsonPath("$.home", is(matchInfo.getHomeCommand())))
               .andExpect(jsonPath("$.guest", is(matchInfo.getGuestCommand())))
               .andExpect(jsonPath("$.score", is(matchInfo.getScore())));
    }

    @Test
    public void deleteMatchInfo_NotFound() throws Exception {
        mockMvc.perform(delete(apiPath + getNotFoundMatchInfoId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteMatchInfo_Success() throws Exception {
        mockMvc.perform(delete(apiPath + matchInfo.getId()))
               .andExpect(status().isOk());

        MatchInfo deleted = matchInfoRepository.findOne(matchInfo.getId());
        Assert.assertNull(deleted);
        Assert.assertEquals(0, matchInfoRepository.count());
    }

    @Test
    public void addMatchInfo_Success() throws Exception {
        AddSoccerStatsRequest addedExpected = new AddSoccerStatsRequest();
        addedExpected.setMatchDate(new GregorianCalendar(2015, 7, 6));
        addedExpected.setHomeCommand("Barcelona");
        addedExpected.setGuestCommand("Real Madrid");
        addedExpected.setScore("2-2");

        byte[] addedContent = JsonUtils.objectToJsonBytes(addedExpected);

        byte[] resultContent = mockMvc.perform(put(apiPath)
                                                  .contentType(contentType)
                                                  .content(addedContent))
                                      .andExpect(status().isOk())
                                      .andExpect(content().contentType(contentType))
                                      .andExpect(jsonPath("$.*", hasSize(1)))
                                      .andReturn()
                                      .getResponse()
                                      .getContentAsByteArray();

        AddSoccerStatsResult addResult = JsonUtils.jsonBytesToObject(resultContent, AddSoccerStatsResult.class);

        MatchInfo addedActual = matchInfoRepository.findOne(addResult.getId());

        Assert.assertEquals(2, matchInfoRepository.count());
        Assert.assertEquals(addedExpected.getMatchDate(), addedActual.getMatchDate());
        Assert.assertEquals(addedExpected.getHomeCommand(), addedActual.getHomeCommand());
        Assert.assertEquals(addedExpected.getGuestCommand(), addedActual.getGuestCommand());
        Assert.assertEquals(addedExpected.getScore(), addedActual.getScore());
    }
}
