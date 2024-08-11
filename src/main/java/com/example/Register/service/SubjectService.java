package com.example.Register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Register.model.Subject;
import com.example.Register.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> getSubjectsByCourseId(Long courseId) {
        return subjectRepository.findByCourseId(courseId);
    }

    public void createSubject(Subject subject) {
        subjectRepository.save(subject);
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}

