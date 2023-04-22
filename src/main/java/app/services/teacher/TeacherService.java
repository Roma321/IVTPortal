package app.services.teacher;


import app.models.Teacher;

public interface TeacherService {

    Teacher addTeacher(Teacher teacher);
    void deleteTeacher(Integer id);
    Iterable<Teacher> getAll();
    Teacher getByFullName(String firstName, String lastName, String patronymic);

}
