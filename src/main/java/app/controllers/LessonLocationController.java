package app.controllers;

import app.models.LessonLocation;
import app.repositories.LessonLocationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LessonLocationController {

    private final LessonLocationRepository repository;

    public LessonLocationController(LessonLocationRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/location/add")
    public String addLocation(@RequestBody LessonLocation lessonLocation) {

        repository.save(lessonLocation);

        return "lesson_location/lesson_location";
    }
}
