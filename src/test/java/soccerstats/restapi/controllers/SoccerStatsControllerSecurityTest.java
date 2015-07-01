package soccerstats.restapi.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import soccerstats.SecurityConfig;
import soccerstats.restapi.dto.AddSoccerStatsRequest;
import utils.JsonUtils;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = SecurityConfig.class)
public class SoccerStatsControllerSecurityTest extends SoccerStatsControllerTestBase {

    private static final String AUTH_HEADER = "Authorization";

    private MockMvc mockMvcSecured;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Before
    @Override
    public void setup() throws Exception {
        super.setup();

        mockMvcSecured = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                        .dispatchOptions(true)
                                        .addFilters(filterChainProxy)
                                        .build();
    }

    @Test
    public void getMatchInfo_Unauthorized_Success() throws Exception {
        mockMvcSecured.perform(get(API_PATH + matchInfo.getId()))
                      .andExpect(status().isOk());
    }

    @Test
    public void deleteMatchInfo_Unauthorized_NotAllowed() throws Exception {
        mockMvcSecured.perform(delete(API_PATH + matchInfo.getId()))
                      .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteMatchInfo_Authorized_Success() throws Exception {
        mockMvcSecured.perform(delete(API_PATH + matchInfo.getId())
                                    .header(AUTH_HEADER, getBasicAuthHeader()))
                      .andExpect(status().isOk());
    }

    @Test
    public void addMatchInfo_Unauthorized_NotAllowed() throws Exception {
        AddSoccerStatsRequest addedExpected = getAddSoccerStatsRequest();
        byte[] addedContent = JsonUtils.objectToJsonBytes(addedExpected);

        mockMvcSecured.perform(put(API_PATH)
                                    .contentType(CONTENT_JSON)
                                    .content(addedContent))
                      .andExpect(status().isUnauthorized());
    }

    @Test
    public void addMatchInfo__Authorized_Success() throws Exception {
        AddSoccerStatsRequest addedExpected = getAddSoccerStatsRequest();
        byte[] addedContent = JsonUtils.objectToJsonBytes(addedExpected);

        mockMvcSecured.perform(put(API_PATH)
                                    .header(AUTH_HEADER, getBasicAuthHeader())
                                    .contentType(CONTENT_JSON)
                                    .content(addedContent))
                .andExpect(status().isOk());
    }

    public static String getBasicAuthHeader() {
        String plainCreds = "user:123";
        String base64Creds = Base64.getEncoder().encodeToString(plainCreds.getBytes());

        return "Basic " + base64Creds;
    }
}
