package com.luv2code.springmvc.models.grade;

public interface Grade {
    double getGrade();

    int getId();

    void setId(Integer id);

    int getStudentId();

    void setStudentId(int studentId);

    void setGrade(double grade);
}
