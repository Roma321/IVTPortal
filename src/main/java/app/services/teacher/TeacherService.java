package app.services.teacher;


import app.models.LessonSchedule;
import app.models.Teacher;

public interface TeacherService {

    Teacher addTeacher(Teacher teacher);
    void deleteTeacher(Integer id);
    Teacher getById(Integer id);
    Iterable<Teacher> getAll();
    Teacher getByFullName(String firstName, String lastName, String patronymic);

}
