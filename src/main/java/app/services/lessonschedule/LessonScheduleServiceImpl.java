package app.services.lessonschedule;


import app.models.LessonSchedule;
import app.repositories.LessonScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class LessonScheduleServiceImpl implements LessonScheduleService {
    private final LessonScheduleRepository repository;

    public LessonScheduleServiceImpl(LessonScheduleRepository repository) {
        this.repository = repository;
    }


    @Override
    public LessonSchedule addLessonSchedule(LessonSchedule lessonSchedule) {
        return this.repository.save(lessonSchedule);
    }

    @Override
    public void deleteAuditorium(Integer id) {
           this.repository.deleteById(id);
    }

    @Override
    public LessonSchedule getById(Integer id) {
        return this.repository.findById(id).orElseThrow();
    }

    @Override
    public Iterable<LessonSchedule> getAll() {
        return repository.findAll();
    }
}
