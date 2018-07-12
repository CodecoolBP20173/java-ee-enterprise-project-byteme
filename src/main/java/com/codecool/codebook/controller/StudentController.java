package com.codecool.codebook.controller;

import com.codecool.codebook.model.Student;
import com.codecool.codebook.model.Workplace;
import com.codecool.codebook.repository.StudentRepository;
import com.codecool.codebook.repository.WorkplaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    WorkplaceRepository workplaceRepository;

    @Autowired
    HttpSession session;

    @GetMapping("/")
    public String listStudents(Model model) {
        List<Student> students = studentRepository.findAll();

        model.addAttribute("students", students);
        String email = (String) session.getAttribute("email");
        model.addAttribute("user", studentRepository.findByEmail(email));
        return "index";
    }

    @GetMapping("/student")
    public String showStudent(Model model, @RequestParam("id") String id){
        Student student = studentRepository.getOne(Long.valueOf(id));
        model.addAttribute("student", student);
        model.addAttribute("sessinId", session.getAttribute("userID"));
        return "student";
    }

    @PostMapping("/edit")
    public String edit(Model model, @RequestParam("id") String id){
        Student student = studentRepository.getOne(Long.valueOf(id));
        List<Workplace> workplaceList = workplaceRepository.findAll();
        Workplace workplace = student.getWorkplace();
        model.addAttribute("student", student);
        model.addAttribute("workplaces", workplaceList);
        return "edit";
    }



}