package soccerstats.webapp.controllers;

import soccerstats.repository.MatchInfoRepository;
import soccerstats.repository.dao.MatchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainPageController {

    MatchInfoRepository matchInfoRepository;

    @Autowired
    public MainPageController(MatchInfoRepository matchInfoRepository) {
        this.matchInfoRepository = matchInfoRepository;
    }

    @ModelAttribute("helloString")
    public String getHelloString() {
        return "from shenry";
    }

    @RequestMapping
    public String get(ModelMap model) {

        List<MatchInfo> matches = matchInfoRepository.findAll();

        model.put("matches", matches);

        return "index.jsp";
    }
}
