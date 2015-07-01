package soccerstats.restapi.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import soccerstats.repository.dao.MatchInfo;
import soccerstats.restapi.dto.AddSoccerStatsRequest;
import soccerstats.restapi.dto.AddSoccerStatsResult;
import utils.JsonUtils;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class SoccerStatsControllerTest extends SoccerStatsControllerTestBase {

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        super.setup();

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                 .build();
    }

    private int getNotFoundMatchInfoId() {
        return matchInfo.getId() + 1;
    }

    @Test
    public void getMatchInfo_NotFound() throws Exception {
        mockMvc.perform(get(getMatchUrl(getNotFoundMatchInfoId())))
               .andExpect(status().isNotFound());
    }

    @Test
    public void getMatchInfo_Success() throws Exception {
        mockMvc.perform(get(getMatchUrl(matchInfo.getId())))
               .andExpect(status().isOk())
               .andExpect(content().contentType(CONTENT_JSON))
               .andExpect(jsonPath("$.*", hasSize(5)))
               .andExpect(jsonPath("$.id", is(matchInfo.getId())))
               .andExpect(jsonPath("$.date", is(matchInfo.getMatchDate().getTimeInMillis())))
               .andExpect(jsonPath("$.home", is(matchInfo.getHomeCommand())))
               .andExpect(jsonPath("$.guest", is(matchInfo.getGuestCommand())))
               .andExpect(jsonPath("$.score", is(matchInfo.getScore())));
    }

    @Test
    public void deleteMatchInfo_NotFound() throws Exception {
        mockMvc.perform(delete(getMatchUrl(getNotFoundMatchInfoId())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteMatchInfo_Success() throws Exception {
        mockMvc.perform(delete(getMatchUrl(matchInfo.getId())))
               .andExpect(status().isOk());

        MatchInfo deleted = matchInfoRepository.findOne(matchInfo.getId());
        Assert.assertNull(deleted);
        Assert.assertEquals(0, matchInfoRepository.count());
    }

    @Test
    public void addMatchInfo_Success() throws Exception {
        AddSoccerStatsRequest addedExpected = getAddSoccerStatsRequest();

        byte[] addedContent = JsonUtils.objectToJsonBytes(addedExpected);

        byte[] resultContent = mockMvc.perform(put(API_PATH)
                                                  .contentType(CONTENT_JSON)
                                                  .content(addedContent))
                                      .andExpect(status().isOk())
                                      .andExpect(content().contentType(CONTENT_JSON))
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
