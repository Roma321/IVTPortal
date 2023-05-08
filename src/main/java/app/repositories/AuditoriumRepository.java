package app.repositories;

import app.models.Auditorium;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuditoriumRepository extends CrudRepository<Auditorium, Integer> {

    Auditorium findByAuditoriumNumber(String id);
    @Query("SELECT auditoriumNumber FROM Auditorium")
    List<String> findAuditoriumNumbers();


}
