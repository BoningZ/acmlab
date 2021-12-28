package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Interviewee;
import org.fatmansoft.teach.models.Mark;
import org.fatmansoft.teach.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<Mark,Integer> {
    Boolean existsByStudentAndInterviewee(Student student, Interviewee interviewee);
    Optional<Mark> findByStudentAndInterviewee(Student student,Interviewee interviewee);
    List<Mark> findMarksByInterviewee(Interviewee interviewee);
    @Modifying
    void deleteMarksByInterviewee(Interviewee interviewee);
}
