package app.repositories;

import app.models.LessonSchedule;
import app.models.Teacher;
import app.models.enums.LessonType;
import app.models.enums.WeekDay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonScheduleRepository extends CrudRepository<LessonSchedule, Integer> {

    LessonSchedule findByTeacherAndWeekDayAndLessonNumberAndLessonType(
            Teacher teacher, WeekDay weekDay, int lessonNumber, LessonType lessonType
    );


    List<LessonSchedule> findLessonSchedulesByGroupsGroupId(int group);
    List<LessonSchedule> findByAuditoriumAuditoriumNumber(String auditoriumNumber);
}
