package com.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.student.entity.Student;
import com.student.service.StudentService;

@Controller
public class StudentController
{
	private StudentService studentService;
	
		
	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}

	//handler method to handle list of students and return mode and view
	@GetMapping("/students")
	public String listStudents(Model model)
	{
		model.addAttribute("students", studentService.getAllStudents());
		return "/students";
	}
	
	@GetMapping("/students/new")
	public String createStudentForm(Model model)
	{
		//create student object to hold student form data
		Student student = new Student();
		model.addAttribute("student", student);
		return "createstudent";
	}
	
	
	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") Student student)
	{
		studentService.saveStudent(student);
		return "redirect:/students";
		
	}
	
	//handler method for get the id of student
	@GetMapping("/students/edit/{id}")
	public String editStudentForm(@PathVariable int id, Model model)
	{
		model.addAttribute("student", studentService.getStudentById(id));
		return "editstudent";
	}
	
	
	//handler method for update student details
	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable int id, @ModelAttribute("student") Student student, Model model)
	{
		//get student from database by id
		Student existingStudent = studentService.getStudentById(id);
		existingStudent.setId(id);
		existingStudent.setName(student.getName());
		existingStudent.setDob(student.getDob());
		existingStudent.setClassName(student.getClassName());
		existingStudent.setPhone(student.getPhone());
		existingStudent.setEmail(student.getEmail());
		
		//save updated student object
		studentService.updateStudent(existingStudent);
		return "redirect:/students";
	}
	
	
	//handler method for delete student details 
	@GetMapping("/students/{id}")
	public String deleteStudent(@PathVariable int id)
	{
		studentService.deleteStudentById(id);
		return "redirect:/students";
	}
	
	
}
