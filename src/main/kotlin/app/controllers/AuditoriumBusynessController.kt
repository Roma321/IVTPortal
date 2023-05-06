package app.controllers

import app.repositories.LessonScheduleRepository
import app.util.getDisplaySchedule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import kotlin.jvm.optionals.getOrNull

@Controller
class AuditoriumBusynessController {
    private val mapper = jacksonObjectMapper()

    @Autowired
    lateinit var lessonScheduleRepository: LessonScheduleRepository

    @GetMapping("/busyness/auditoriums")
    fun showEditorWithSchedule(model: Model): String {
        val currentSchedule = lessonScheduleRepository.findAll().groupBy { it.auditorium.auditoriumNumber }
            .mapValues { auditoriumList ->
                auditoriumList.value.groupBy { it.weekDay }
                    .mapValues { weekDayListEntry ->
                        weekDayListEntry.value.groupBy { it.lessonNumber }
                            .mapValues { lessonListEntry -> lessonListEntry.value.map { it.lessonType } }
                    }
            }
        model["items"] = mapper.writeValueAsString(currentSchedule)
        return "busyness"
    }
}