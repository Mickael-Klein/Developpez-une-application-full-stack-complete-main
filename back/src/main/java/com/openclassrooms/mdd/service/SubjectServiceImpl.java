package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.model.Subject;
import com.openclassrooms.mdd.repository.SubjectRepository;
import com.openclassrooms.mdd.serviceInterface.SubjectInterface;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl implements SubjectInterface {

  @Autowired
  private SubjectRepository subjectRepository;

  @Override
  public List<Subject> getAllSubject() {
    return subjectRepository.findAll();
  }

  @Override
  public Optional<Subject> getSubjectById(long id) {
    return subjectRepository.findById(id);
  }
}
