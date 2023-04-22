package app.services.lessonschedule;

import app.models.LessonSchedule;

public interface LessonScheduleService {
    LessonSchedule addLessonSchedule(LessonSchedule lessonSchedule);
    void deleteAuditorium(Integer id);
    Iterable<LessonSchedule> getAll();
}
