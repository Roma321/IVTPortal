package app.controllers;

import app.models.Teacher;
import app.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherController {
    private final TeacherRepository repository;

    public TeacherController(TeacherRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/teacher/add")
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        return repository.save(teacher);
    }

    @PostMapping("/teacher/add-several")
    public Iterable<Teacher> addSeveralTeachers(@RequestBody List<Teacher> teachers) {
        return repository.saveAll(teachers);
    }

    @DeleteMapping("/teacher/delete")
    @Transactional
    public void deleteTeacher(@RequestBody Teacher teacher) {
        System.out.println(teacher);
        if (teacher.getPatronymic() == null) {
            repository.deleteTeacherByFirstNameIgnoreCaseAndLastNameIgnoreCase(teacher.getFirstName(), teacher.getLastName());
        } else {
            repository.delete(teacher);
        }
    }

    @GetMapping("/teacher/all")
    public Iterable<Teacher> getAllTeachers() {
        return repository.findAll();
    }


}
