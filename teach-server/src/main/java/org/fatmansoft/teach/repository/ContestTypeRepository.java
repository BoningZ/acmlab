package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.ContestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContestTypeRepository extends JpaRepository<ContestType,Integer> {
    Optional<ContestType> findByName(String name);
}
