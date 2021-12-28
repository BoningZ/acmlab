package org.fatmansoft.teach.repository;


import org.fatmansoft.teach.models.Interviewee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IntervieweeRepository extends JpaRepository<Interviewee, Integer> {
    Boolean existsBySid(String sid);
    List<Interviewee> getAllByGrade(Integer grade);
    Optional<Interviewee> getBySid(String sid);
}
