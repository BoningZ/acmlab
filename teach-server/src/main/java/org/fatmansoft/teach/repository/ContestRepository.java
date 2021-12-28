package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Contest;
import org.fatmansoft.teach.models.ContestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestRepository extends JpaRepository<Contest,Integer> {
    List<Contest> getContestBySeasonAndContestType(Integer season, ContestType contestType);
    List<Contest> getContestBySeason(Integer season);
    List<Contest> getContestByContestType(ContestType contestType);
}
