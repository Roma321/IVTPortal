package app.controllers;

import app.services.lessonschedule.LessonScheduleService;
import org.springframework.stereotype.Controller;

@Controller
public class LessonScheduleController {
    private final LessonScheduleService lessonScheduleService;

    public LessonScheduleController(LessonScheduleService service) {
        lessonScheduleService = service;
    }
}
