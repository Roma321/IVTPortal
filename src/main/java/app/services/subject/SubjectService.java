package app.services.subject;
import app.models.LessonSchedule;
import app.models.Subject;

public interface SubjectService {
    Subject addSubject(Subject subject);
    void deleteSubject(Integer id);
    Subject getById(Integer id);
    Iterable<Subject> getAll();
}
