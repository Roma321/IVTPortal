package app.repositories;

import app.models.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, Integer> {
    Subject findSubjectBySubjectNameIgnoreCase(String subjectName);

    Subject deleteSubjectBySubjectName(String subjectName);
}
