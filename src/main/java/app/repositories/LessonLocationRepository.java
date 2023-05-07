package app.repositories;

import app.models.LessonLocation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonLocationRepository extends CrudRepository<LessonLocation, Integer> {

    LessonLocation findOneByLessonLocation(String location);
}
