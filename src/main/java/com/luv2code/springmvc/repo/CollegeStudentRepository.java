package com.luv2code.springmvc.repo;

import com.luv2code.springmvc.models.student.CollegeStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollegeStudentRepository extends JpaRepository<CollegeStudent, Integer> {
    Optional<CollegeStudent> findByEmailAddress(String emailAddress);

}
