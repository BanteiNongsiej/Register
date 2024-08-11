package com.example.Register.service;

import com.example.Register.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Register.model.Subject;
import com.example.Register.repository.CourseRepository;

import java.util.List;
import java.util.Set;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Create a new course
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return Optional.ofNullable(courseRepository.findById(id));
    }

    public void createCourse(Course course) {
        courseRepository.save(course);
    }

    public void updateCourse(Course course) {
        courseRepository.update(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public Set<Subject> getSubjectsByCourseId(Long courseId) {
        return courseRepository.findSubjectsByCourseId(courseId);
    }
}
