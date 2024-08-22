package com.example.Register.controller;

import ch.qos.logback.classic.Logger;
import com.example.Register.helper.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.Register.model.Course;
import com.example.Register.model.Student;
import com.example.Register.model.Subject;
import com.example.Register.service.StudentService;
import com.example.Register.service.SubjectService;
import com.example.Register.service.CourseService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class StudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @Autowired
    SubjectService subjectService;

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student){

        studentService.createStudent(student);
        List<Student> students = studentService.getAllStudents();
        return "redirect:/createStudent";
    }
    
    
//    @PostMapping("/saveStudent")
//    public ResponseEntity<Student> saveStudent(@RequestBody Student student){
//    	studentService.createStudent(student);
//    	return ResponseEntity.ok(student);
//    }

    @PostMapping("/updateStudent")
    public String updateStudent(@ModelAttribute("student") Student student){

        studentService.updateStudent(student);
        List<Student> students = studentService.getAllStudents();
        return "redirect:/createStudent";
    }
    
//    @PostMapping("/updateStudent")
//    public ResponseEntity<Student> updateStudent (@RequestBody Student student){
//		studentService.updateStudent(student);
//    	return ResponseEntity.ok(student);
//    	
//    }
    
    @GetMapping("/createStudent")
    public String createStudent(Model model) {
        try {
            System.out.println("CREATE");
            if (AuthenticationHelper.authenticated()) {
                System.out.println("AUTHENTICATED!");
            Student newStudent = new Student();
        List<Course> courseList = courseService.getAllCourses();
        model.addAttribute("student", newStudent);
        model.addAttribute("newStudent", true);
        model.addAttribute("courses", courseList );
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("listStudents", students);
        return "StudentForm";}
        } catch (Exception e) {
            return "redirect:/login?logout";
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/subjectList/{course_id}")
    public ResponseEntity<Set<Subject>> getSubjectsByCourseId(@PathVariable("course_id") Long courseId) {
        try {
            System.out.println("inside");
//            Course course = courseService.getCourseById(courseId).get();
//            System.out.println("tostring:"+course.toString());
//            if (course == null) {
//                System.out.println("notFound");
//                return ResponseEntity.notFound().build();
//            }
            System.out.println(courseId);
            Set<Subject> subjects = courseService.getSubjectsByCourseId(courseId);
            System.out.println(subjects.toString());
            return ResponseEntity.ok(subjects);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            model.addAttribute("courses", courseService.getAllCourses());
            model.addAttribute("subjects", subjectService.getSubjectsByCourseId(student.get().getCourseId())); // Ensure subjects are populated based on course
            model.addAttribute("listStudents", studentService.getAllStudents());
            model.addAttribute("newStudent", false);
            return "StudentForm";
        }
        return "redirect:/createStudent";
    }


    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
    	System.out.println("Deleting student with ID: " + id);
        studentService.deleteStudent(id);
        return "redirect:/createStudent";
    }
    
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
//        studentService.deleteStudent(id);
//        return ResponseEntity.ok().build();
//    }


    @GetMapping("/favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
        // Do nothing - just return an empty response to prevent 404 errors
    }
}
