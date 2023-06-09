package app.services.teacher;

import app.models.Teacher;
import app.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService{

    private final TeacherRepository repository;
    public TeacherServiceImpl(TeacherRepository repository) {
        this.repository = repository;
    }
    @Override
    @Transactional
    public Teacher addTeacher(Teacher teacher) {
           return this.repository.save(teacher);
    }

    @Override
    @Transactional
    public void deleteTeacher(Integer id) { this.repository.deleteById(id);}

    @Override
    public Teacher getById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Iterable<Teacher> getAll() {
        return this.repository.findAll();
    }
    @Override
    public Teacher getByFullName(String firstName, String lastName, String patronymic) {
        return this.repository.getTeachersByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndPatronymicIgnoreCase(firstName, lastName, patronymic);
    }
}
