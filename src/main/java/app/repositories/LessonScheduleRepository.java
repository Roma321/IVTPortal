package app.repositories;

import app.models.LessonSchedule;
import org.springframework.data.repository.CrudRepository;

public interface LessonScheduleRepository extends CrudRepository<LessonSchedule, Integer> {

}
