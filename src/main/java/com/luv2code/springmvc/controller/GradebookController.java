package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.models.Gradebook;
import com.luv2code.springmvc.models.student.CollegeStudent;
import com.luv2code.springmvc.service.StudentAndGradesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class GradebookController {
	private final Gradebook gradebook;
	private final StudentAndGradesService studentService;

	public GradebookController(Gradebook gradebook, StudentAndGradesService studentService) {
		this.gradebook = gradebook;
		this.studentService = studentService;
	}

	@GetMapping("/")
	public String getStudents(Model m) {
		m.addAttribute("students", studentService.findAll());
		return "index";
	}

	@PostMapping("/")
	public String createStudent(@ModelAttribute("student") CollegeStudent collegeStudent, Model m) {
		studentService.saveStudent(collegeStudent);
		m.addAttribute("students", studentService.findAll());
		return "index";
	}

	@GetMapping("/delete/student/{id}")
	public String deleteStudent(@PathVariable Integer id, Model m) {
		studentService.deleteStudentById(id);
		m.addAttribute("students", studentService.findAll());
		return "index";
	}
}
