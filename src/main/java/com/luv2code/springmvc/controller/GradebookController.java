package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.models.student.CollegeStudent;
import com.luv2code.springmvc.service.StudentAndGradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class GradebookController {

	@Autowired
	private Gradebook gradebook;

	@Autowired
	private StudentAndGradesService studentService;

	@GetMapping("/")
	public String getStudents(Model m) {
		m.addAttribute("students", studentService.findAll());
		return "index";
	}


	@GetMapping("/studentInformation/{id}")
	public String studentInformation(@PathVariable Integer id, Model m) {
		return "studentInformation";
	}

}
