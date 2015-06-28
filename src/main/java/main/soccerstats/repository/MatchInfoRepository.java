package main.soccerstats.repository;

import main.soccerstats.repository.dao.MatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchInfoRepository extends JpaRepository<MatchInfo, Integer> {
}
