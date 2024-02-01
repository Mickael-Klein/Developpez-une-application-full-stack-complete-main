package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {}
