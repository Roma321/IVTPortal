package app.services.lessonschedule;

import app.models.LessonSchedule;

public interface LessonScheduleService {
    LessonSchedule addLessonSchedule(LessonSchedule lessonSchedule);
    void deleteAuditorium(Integer id);
    LessonSchedule getById(Integer id);
    Iterable<LessonSchedule> getAll();
}
