package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.models.student.CollegeStudent;
import com.luv2code.springmvc.repo.CollegeStudentRepository;
import com.luv2code.springmvc.service.StudentAndGradesService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@AutoConfigureMockMvc
@SpringBootTest
@SqlGroup({
        @Sql(scripts = {"/test/sql/controllers_data.sql"}, executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = {"/test/sql/clean_student_table.sql"}, executionPhase = AFTER_TEST_METHOD)})
class GradebookControllerTest {
    private static MockHttpServletRequest createStudentReq;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentAndGradesService studentServiceMock;

    @Autowired
    private CollegeStudentRepository studentRepo;

    @BeforeAll
    public static void beforeAll() {
        createStudentReq = new MockHttpServletRequest();
        createStudentReq.addParameter("firstname", "Ali");
        createStudentReq.addParameter("lastname", "Darby");
        createStudentReq.addParameter("emailAddress", "alihmzyv@gmail.com");
    }


    @Test
    void testGetStudents1() throws Exception {
        CollegeStudent student1 = new CollegeStudent("Ali", "Hamzayev", "alihmzyv@gmail.com");
        CollegeStudent student2 = new CollegeStudent("Konul", "Hamzayeva", "knl@gmail.com");
        List<CollegeStudent> studentList = new ArrayList<>(Arrays.asList(student1, student2));
        when(studentServiceMock.findAll()).thenReturn(studentList);
        MvcResult mvc = mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        ModelAndView mav = mvc.getModelAndView();
        assert mav != null;
        ModelAndViewAssert.assertViewName(mav, "index");
    }

    @Test
    void testCreateStudent1() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstname", createStudentReq.getParameter("firstname"))
                        .param("lastname", createStudentReq.getParameter("lastname"))
                        .param("firstname", createStudentReq.getParameter("emailAddress")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        assert mav != null;
        ModelAndViewAssert.assertViewName(mav, "index");
        assertNotNull(studentRepo.findByEmailAddress(createStudentReq.getParameter("emailAddress")),
                "Student should have been saved");
    }

    @Test
    void testDeleteStudent() throws Exception {
        assertTrue(studentRepo.existsById(1));
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/delete/student/{id}", 1)) //actually wrong to use get for delete
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertFalse(studentRepo.existsById(1));
        ModelAndView mav = mvcResult.getModelAndView();
        assert mav != null;
        ModelAndViewAssert.assertViewName(mav, "index");
    }

    @Test
    void testDeleteStudentNotFoundId() throws Exception {
        assertFalse(studentRepo.existsById(32));
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/delete/student/{id}", 31)) //actually wrong to use get for delete
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        assert mav != null;
        ModelAndViewAssert.assertViewName(mav, "error");
    }
}