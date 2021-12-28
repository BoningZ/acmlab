package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Ranking;
import org.fatmansoft.teach.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Integer> {
    Optional<Ranking> getByTeamAndSeasonAndCountInSeason(Team team, Integer season, Integer countInSeason);
    List<Ranking> getRankingsBySeason(Integer season);
    List<Ranking> getRankingsBySeasonAndCountInSeason(Integer season, Integer countInSeason);
    List<Ranking> getRankingsByTeamAndSeason(Team team, Integer season);
    Boolean existsByTeamAndSeason(Team team, Integer season);
}
