package app.services.subject;

import app.models.Subject;
import app.repositories.SubjectRepository;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository repository;

    public SubjectServiceImpl(SubjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public Subject addSubject(Subject subject) {
        return repository.save(subject);
    }

    @Override
    public void deleteSubject(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Subject getById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Iterable<Subject> getAll() {
        return repository.findAll();
    }
}
