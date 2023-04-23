package app.services.resit;

import app.models.LessonSchedule;
import app.models.Resit;

import java.util.List;

public interface ResitService {
    Resit addResit(Resit resit);

    void deleteResit(Integer id);
    void updateResit(Resit resit);
    Resit getById(Integer id);
    Iterable<Resit> getAll();

}
