package com.example.Register.controller;

import com.example.Register.helper.AuthenticationHelper;
import com.example.Register.model.Course;
import com.example.Register.model.Student;
import com.example.Register.model.Subject;
import com.example.Register.service.StudentService;
import com.example.Register.service.SubjectService;
import com.example.Register.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/saveStudent")
    @ResponseBody
    public ResponseEntity<String> saveStudent(@ModelAttribute("student") Student student) {
    	studentService.createStudent(student);
        return ResponseEntity.ok("Student saved successfully.");
    }

    @PostMapping("/updateStudent")
    @ResponseBody
    public ResponseEntity<String> updateStudent(@ModelAttribute("student") Student student) {
    	studentService.updateStudent(student);
        return ResponseEntity.ok("Student updated successfully.");
    }

    @GetMapping("/createStudent")
    public String createStudent(@RequestParam(required = false) Long id, Model model) {
        if (!AuthenticationHelper.authenticated()) {
            return "redirect:/login?logout";
        }

        Student student = id != null ? studentService.getStudentById(id).orElse(new Student()) : new Student();
        model.addAttribute("student", student);
        model.addAttribute("newStudent", id == null); // true if creating new student

        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("listStudents", studentService.getAllStudents());

        return "StudentForm";
    }

    @GetMapping("/subjectList/{course_id}")
    public ResponseEntity<Set<Subject>> getSubjectsByCourseId(@PathVariable("course_id") Long courseId) {
    	Set<Subject> subjects = courseService.getSubjectsByCourseId(courseId);
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/getStudent/{id}")
    public ResponseEntity<Optional<Student>> getStudent(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
    	studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully.");    }

    @GetMapping("/listStudents")
    @ResponseBody
    public ResponseEntity<List<Student>> listStudents() {
    	List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
}
