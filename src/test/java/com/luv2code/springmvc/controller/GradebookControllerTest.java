package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.models.student.CollegeStudent;
import com.luv2code.springmvc.service.StudentAndGradesService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
class GradebookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentAndGradesService studentServiceMock;

    @BeforeEach
    @Sql("/test/sql/controllers_data.sql")
    public void beforeEach() {
    }

    @AfterEach
    @Sql(statements = {"delete from student"})
    public void afterEach() {
    }

    @Test
    void testGetStudents1() throws Exception {
        CollegeStudent student1 = new CollegeStudent("Ali", "Hamzayev", "alihmzyv@gmail.com");
        CollegeStudent student2 = new CollegeStudent("Konul", "Hamzayeva", "knl@gmail.com");
        List<CollegeStudent> studentList = new ArrayList<>(Arrays.asList(student1, student2));
        when(studentServiceMock.findAll()).thenReturn(studentList);
        MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        ModelAndView mav = mvc.getModelAndView();
        assert mav != null;
        ModelAndViewAssert.assertViewName(mav, "index");
        ModelAndViewAssert.assertModelAttributeValues(mav, Map.of("students", studentList));
    }
}