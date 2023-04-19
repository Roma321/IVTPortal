package app.controllers;

import app.models.Subject;
import app.repositories.SubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubjectController {
    private final SubjectRepository repository;

    public SubjectController(SubjectRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/subject/add")
    public Subject addSubject(@RequestBody Subject subject) {
        return repository.save(subject);
    }

    @DeleteMapping("/subject/delete")
    @Transactional
    public Subject deleteSubject(@RequestBody Subject subject) {
        return repository.deleteSubjectBySubjectName(subject.getSubjectName());
    }

    @GetMapping("/subject/all")
    public Iterable<Subject> getAllSubjects() {
        return repository.findAll();
    }

    @GetMapping("/subject")
    public Subject getSubject(@RequestBody String subjectName) {
        return repository.findSubjectBySubjectNameIgnoreCase(subjectName);
    }
}
