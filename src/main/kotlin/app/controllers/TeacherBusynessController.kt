package app.controllers

import app.models.LessonSchedule
import app.models.Teacher
import app.repositories.LessonScheduleRepository
import app.repositories.TeacherRepository
import app.util.groupBusynessWithKeyExtract
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TeacherBusynessController {
    private val mapper = jacksonObjectMapper()

    @Autowired
    lateinit var lessonScheduleRepository: LessonScheduleRepository

    @Autowired
    lateinit var teacherRepository: TeacherRepository

    @GetMapping("/busyness/teachers")
    fun showTeachersBusyness(model: Model): String {
        val allTeachers = teacherRepository.findAll().map { toShortName(it) }
        val currentSchedule =
            groupBusynessWithKeyExtract(lessonScheduleRepository.findAll().toList(),allTeachers, ::getTeacherShortFullNameForLesson)
        model["items"] = mapper.writeValueAsString(currentSchedule)
        return "teacher/teacher_busyness"
    }

    private fun getTeacherShortFullNameForLesson(it: LessonSchedule): String {
        val t = it.teacher
        return toShortName(t)
    }

    private fun toShortName(t: Teacher) = "${t.lastName} ${t.firstName[0]}. ${t.patronymic[0]}."
}