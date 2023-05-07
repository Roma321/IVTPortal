package app.controllers

import app.models.LessonSchedule
import app.repositories.LessonScheduleRepository
import app.util.groupBusynessWithKeyExtract
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class BusynessController {
    private val mapper = jacksonObjectMapper()

    @Autowired
    lateinit var lessonScheduleRepository: LessonScheduleRepository

    @GetMapping("/busyness/auditoriums")
    fun showAuditoriumsBusyness(model: Model): String {
        val currentSchedule =
            groupBusynessWithKeyExtract(lessonScheduleRepository.findAll().toList(), ::getAuditoriumNumber)
        model["items"] = mapper.writeValueAsString(currentSchedule)
        return "busyness"
    }

    @GetMapping("/busyness/teachers")
    fun showTeachersBusyness(model: Model): String {
        val currentSchedule =
            groupBusynessWithKeyExtract(lessonScheduleRepository.findAll().toList(), ::getTeacherShortFullNameForLesson)
        model["items"] = mapper.writeValueAsString(currentSchedule)
        return "busyness"
    }

    @GetMapping("/busyness/groups")
    fun showGroupsBusyness(model: Model): String {
        val a = lessonScheduleRepository.findAll().toList()
        val listWithoutMultipleGroups = mutableListOf<LessonSchedule>()
        for (lesson in a) {
            for (group in lesson.groups) {
                val newLesson = lesson.deepCopy()
                newLesson.groups = listOf(group)
                listWithoutMultipleGroups.add(newLesson)
            }
        }
        val currentSchedule = groupBusynessWithKeyExtract(listWithoutMultipleGroups, ::getGroupName)
        model["items"] = mapper.writeValueAsString(currentSchedule)
        return "busyness"
    }

    private fun getAuditoriumNumber(it: LessonSchedule): Int? = it.auditorium.auditoriumNumber
    private fun getTeacherShortFullNameForLesson(it: LessonSchedule): String {
        val t = it.teacher
        return "${t.lastName} ${t.firstName[0]}. ${t.patronymic[0]}."
    }

    private fun getGroupName(it: LessonSchedule): String = it.groups[0].groupName

}