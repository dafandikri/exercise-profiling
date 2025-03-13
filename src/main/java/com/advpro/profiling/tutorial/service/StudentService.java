package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.model.StudentCourse;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author muhammad.khadafi
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<StudentCourse> getAllStudentsWithCourses() {
        // Fetch all student courses in a single database call
        List<StudentCourse> allStudentCourses = studentCourseRepository.findAll();

        // Create a map of student IDs to their objects to avoid multiple lookups
        Map<Long, Student> studentMap = new HashMap<>();
        for (StudentCourse sc : allStudentCourses) {
            if (!studentMap.containsKey(sc.getStudent().getId())) {
                studentMap.put(sc.getStudent().getId(), sc.getStudent());
            }
        }

        // Now we can reuse student objects without creating redundant ones
        return allStudentCourses;
    }

    public Optional<Student> findStudentWithHighestGpa() {
        // Assuming we can add this method to StudentRepository
        return studentRepository.findTopByOrderByGpaDesc();
    }

    public String joinStudentNames() {
        List<Student> students = studentRepository.findAll();

        // Use StringBuilder for efficient string concatenation
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < students.size(); i++) {
            result.append(students.get(i).getName());
            if (i < students.size() - 1) {
                result.append(", ");
            }
        }

        return result.toString();
    }
}
