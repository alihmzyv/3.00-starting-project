package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.student.CollegeStudent;
import com.luv2code.springmvc.repo.CollegeStudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentAndGradesService {
    private final CollegeStudentRepository collegeStudentRepo;

    public StudentAndGradesService(CollegeStudentRepository collegeStudentRepo) {
        this.collegeStudentRepo = collegeStudentRepo;
    }

    public CollegeStudent saveStudent(CollegeStudent collegeStudent) {
        return collegeStudentRepo.save(collegeStudent);
    }

    public boolean studentExistsById(Integer id) {
        return collegeStudentRepo.existsById(id);
    }

    public void deleteStudentById(Integer id) {
        collegeStudentRepo.deleteById(id);
    }

    public Iterable<CollegeStudent> findAll() {
        return collegeStudentRepo.findAll();
    }
}
