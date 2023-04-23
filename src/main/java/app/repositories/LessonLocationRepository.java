package app.repositories;

import app.models.LessonLocation;
import org.springframework.data.repository.CrudRepository;

public interface LessonLocationRepository extends CrudRepository<LessonLocation, Integer> {
}
