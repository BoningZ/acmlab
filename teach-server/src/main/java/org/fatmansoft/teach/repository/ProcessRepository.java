package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Contest;
import org.fatmansoft.teach.models.Process;
import org.fatmansoft.teach.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<Process,Integer> {
    Boolean existsByContest(Contest contest);
    List<Process> findProcessesByTeam(Team t);
}
