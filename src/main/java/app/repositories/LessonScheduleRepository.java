package app.repositories;

import app.models.LessonSchedule;
import app.models.Teacher;
import app.models.enums.LessonType;
import app.models.enums.WeekDay;
import org.springframework.data.repository.CrudRepository;

public interface LessonScheduleRepository extends CrudRepository<LessonSchedule, Integer> {

    LessonSchedule findByTeacherAndWeekDayAndLessonNumberAndLessonType(
            Teacher teacher, WeekDay weekDay, int lessonNumber, LessonType lessonType
    );
}
