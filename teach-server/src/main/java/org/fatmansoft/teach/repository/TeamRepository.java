package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Student;
import org.fatmansoft.teach.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team,Integer> {
    List<Team> findTeamsByCaptainOrMember1OrMember2(Student captain, Student member1, Student member2);
    List<Team> findTeamsByChineseOrEnglish(String Chinese,String English);
    List<Team> findTeamsByActive(boolean b);
}
