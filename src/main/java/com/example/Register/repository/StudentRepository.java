package com.example.Register.repository;

import com.example.Register.model.Course;
import com.example.Register.model.Subject;
import org.springframework.stereotype.Repository;
import com.example.Register.model.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Student student) {
        String sql = "INSERT INTO students (student_name, student_class, student_roll, course_id, subject_id) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, student.getStudent_name(), student.getStudent_class(), student.getStudent_roll(), student.getCourseId(), student.getSubjectId());
    }

    public int update(Student student) {
        String sql = "UPDATE students SET student_name = ?, student_class = ?, student_roll = ?, course_id = ?, subject_id = ? WHERE student_id = ?;";
        return jdbcTemplate.update(sql, student.getStudent_name(), student.getStudent_class(), student.getStudent_roll(), student.getCourseId(), student.getSubjectId(),student.getStudentId());
    }

    public List<Student> findAll() {
        String sql = "SELECT s.*, c.course_name, c.course_id, sub.subject_id, sub.subject_name  FROM students s JOIN courses c ON s.course_id = c.course_id JOIN subjects sub ON s.subject_id = sub.subject_id;";
        return jdbcTemplate.query(sql, new StudentRowMapper());
    }

    public List<Student> findByCourseId(Long courseId) {
        String sql = "SELECT * FROM students WHERE course_id = ?";
        return jdbcTemplate.query(sql, new Object[]{courseId}, new StudentRowMapper());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Optional<Student> findById(Long id) {
        String sql = "SELECT s.*, c.course_name, sub.subject_name FROM students s JOIN courses c ON s.course_id = c.course_id JOIN subjects sub ON s.subject_id = sub.subject_id WHERE student_id = ?;";
        Student student = jdbcTemplate.queryForObject(sql, new Object[]{id}, new StudentRowMapper());
        return Optional.ofNullable(student);
    }

    private static class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setStudentId(rs.getLong("student_id"));
            student.setStudent_name(rs.getString("student_name"));
            student.setStudent_class(rs.getString("student_class"));
            student.setStudent_roll(rs.getString("student_roll"));
            student.setCourseId(rs.getLong("course_id"));
            student.setSubjectId(rs.getLong("subject_id"));
            Course course = new Course();
            course.setCourseId(rs.getLong("course_id"));
            course.setCourseName(rs.getString("course_name"));
            // Set the course to the student
            student.setCourse(course);
            Subject subject = new Subject();
            subject.setSubjectId(rs.getLong("subject_id"));
            subject.setSubjectName(rs.getString("subject_name"));
            student.setSubject(subject);
            return student;
        }
    }
}
