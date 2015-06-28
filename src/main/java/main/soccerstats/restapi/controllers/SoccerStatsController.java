package main.soccerstats.restapi.controllers;

import main.soccerstats.repository.MatchInfoRepository;
import main.soccerstats.repository.dao.MatchInfo;
import main.soccerstats.restapi.dto.AddSoccerStatsRequest;
import main.soccerstats.restapi.dto.AddSoccerStatsResult;
import main.soccerstats.restapi.dto.GetSoccerStatsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/soccer/info")
public class SoccerStatsController {

    private MatchInfoRepository repository;

    @Autowired
    public SoccerStatsController(MatchInfoRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public AddSoccerStatsResult add(@RequestBody AddSoccerStatsRequest request) {
        MatchInfo matchInfo = new MatchInfo();
        matchInfo.setMatchDate(request.getMatchDate());
        matchInfo.setHomeCommand(request.getHomeCommand());
        matchInfo.setGuestCommand(request.getGuestCommand());
        matchInfo.setScore(request.getScore());

        MatchInfo inserted = repository.save(matchInfo);

        return new AddSoccerStatsResult(inserted.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GetSoccerStatsResult get(@PathVariable int id) {
        MatchInfo matchInfo = repository.findOne(id);

        if (matchInfo == null) {
            throw new ResourceNotFoundException("Match info with id = " + id + " not found");
        }

        return new GetSoccerStatsResult(matchInfo);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        try {
            repository.delete(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException("Match info with id = " + id + " not found");
        }
    }
}
