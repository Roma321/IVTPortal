package app.repositories;

import app.models.Auditorium;
import org.springframework.data.repository.CrudRepository;

public interface AuditoriumRepository extends CrudRepository<Auditorium, Integer> {

    Auditorium findByAuditoriumNumber(String id);


}
