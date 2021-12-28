package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    Boolean existsBySid(String sid);
    List<Student> findStudentsByGrade(Integer grade);

}
