package com.example.Register.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.example.Register.model.Course;
import com.example.Register.model.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class CourseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Course> findAll() {
        String sql = "SELECT * FROM courses";
        return jdbcTemplate.query(sql, new CourseRowMapper());
    }

    public Course findById(Long id) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CourseRowMapper());
    }

    public int save(Course course) {
        String sql = "INSERT INTO courses (name) VALUES (?)";
        return jdbcTemplate.update(sql, course.getCourseName());
    }

    public int update(Course course) {
        String sql = "UPDATE courses SET name = ? WHERE id = ?";
        return jdbcTemplate.update(sql, course.getCourseName(), course.getCourseId());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM courses WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Set<Subject> findSubjectsByCourseId(Long courseId) {
        String sql = "SELECT s.subject_id, s.subject_name FROM subjects s JOIN course_subjects cs ON s.subject_id = cs.subject_id WHERE cs.course_id = ?";
        return new HashSet<>(jdbcTemplate.query(sql, new Object[]{courseId}, new SubjectRowMapper()));
    }

    private static class CourseRowMapper implements RowMapper<Course> {
        @Override
        public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
            Course course = new Course();
            course.setCourseId(rs.getLong("course_id"));
            course.setCourseName(rs.getString("course_name"));
            return course;
        }
    }

    private static class SubjectRowMapper implements RowMapper<Subject> {
        @Override
        public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
            Subject subject = new Subject();
            subject.setSubjectId(rs.getLong("subject_id"));
            subject.setSubjectName(rs.getString("subject_name"));
            return subject;
        }
    }
}
