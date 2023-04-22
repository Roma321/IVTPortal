package app.repositories;

import app.models.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher, Integer> {
    void deleteTeacherByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
    Teacher getTeachersByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndPatronymicIgnoreCase(String firstName, String lastName, String patronymic);
}
