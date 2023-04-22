package app.services.subject;
import app.models.Subject;

public interface SubjectService {
    Subject addSubject(Subject subject);

    void deleteSubject(Integer id);

    Iterable<Subject> getAll();
}
