package main;

import main.soccerstats.repository.MatchInfoRepository;
import main.soccerstats.repository.dao.MatchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.GregorianCalendar;

/**
 * Created by shenry on 27.06.2015.
 */

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    MatchInfoRepository matchRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... strings) throws Exception {
       /*
        MatchInfo matchInfo = new MatchInfo();
        matchInfo.setMatchDate(new GregorianCalendar(2015, 05, 01));
        matchInfo.setHomeCommand("Barcelona");
        matchInfo.setGuestCommand("Real Madrid");
        matchInfo.setScore("2-2");

        matchRepository.save(matchInfo);
        */
    }
}
