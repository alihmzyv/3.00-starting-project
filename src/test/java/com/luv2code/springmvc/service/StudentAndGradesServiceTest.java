package com.luv2code.springmvc.service;


import com.luv2code.springmvc.models.student.CollegeStudent;
import com.luv2code.springmvc.repo.CollegeStudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradesServiceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StudentAndGradesService studentService;

    @Autowired
    private CollegeStudentRepository collegeStudentRepo;

    @BeforeEach
    void setUpRepo() {
        jdbcTemplate.execute("insert into student(id, firstname, lastname, email_address) " +
                "values (1, 'Ali', 'Hamzayev', 'alihmzyv@gmail.com')");
    }

    @AfterEach
    void cleanRepo() {
        jdbcTemplate.execute("delete from student");
    }

    @Test
    void testCreateStudentService() {
        String emailAddress = "knl@gmail.com";
        CollegeStudent student = new CollegeStudent("Konul", "Hamzayeva", emailAddress);
        studentService.saveStudent(student);
        assertTrue(collegeStudentRepo.findByEmailAddress(emailAddress).isPresent(), "Student should have been saved.");
    }

    @Test
    void testStudentExistsById() {
        assertTrue(studentService.studentExistsById(1), "Student should exist.");
        assertFalse(studentService.studentExistsById(2), "Student should not exist.");
    }

    @Test
    void testDeleteStudent() {
        assertTrue(collegeStudentRepo.existsById(1), "Student should exist.");
        studentService.deleteStudentById(1);
        assertFalse(collegeStudentRepo.existsById(1), "Student should have been deleted.");
    }

    @Sql("/test/sql/services_data.sql")
    @Test
    void testFindAll() { //impractical actually
        Iterable<CollegeStudent> iterable = studentService.findAll();//impractical actually
        int count = 0;
        for (CollegeStudent student : iterable) {
            count++;
        }
        assertEquals(11, count);
    }
}
