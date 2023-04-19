package app.repositories;

import app.models.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher, Integer> {
    void deleteTeacherByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}
