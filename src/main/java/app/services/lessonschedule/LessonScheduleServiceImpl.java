package app.services.lessonschedule;


import app.repositories.LessonScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class LessonScheduleServiceImpl implements LessonScheduleService {
    private final LessonScheduleRepository repository;

    public LessonScheduleServiceImpl(LessonScheduleRepository repository) {
        this.repository = repository;
    }


}
