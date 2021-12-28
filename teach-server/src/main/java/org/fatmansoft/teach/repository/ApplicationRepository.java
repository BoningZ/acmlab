package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Application;
import org.fatmansoft.teach.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository  extends JpaRepository<Application,Integer> {
    Optional<Application> findApplicationByTeamAndSeason(Team team, Integer season);
}
