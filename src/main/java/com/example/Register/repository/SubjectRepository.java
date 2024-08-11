package com.example.Register.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.example.Register.model.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SubjectRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Subject> findByCourseId(Long courseId) {
        String sql = "SELECT s.id, s.name FROM subjects s JOIN course_subjects cs ON s.id = cs.subject_id WHERE cs.course_id = ?";
        return jdbcTemplate.query(sql, new Object[]{courseId}, new SubjectRowMapper());
    }

    public int save(Subject subject) {
        String sql = "INSERT INTO subjects (name) VALUES (?)";
        return jdbcTemplate.update(sql, subject.getSubjectName());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM subjects WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private static class SubjectRowMapper implements RowMapper<Subject> {
        @Override
        public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
            Subject subject = new Subject();
            subject.setSubjectId(rs.getLong("id"));
            subject.setSubjectName(rs.getString("name"));
            return subject;
        }
    }
}
