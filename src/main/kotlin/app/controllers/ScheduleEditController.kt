package app.controllers

import app.models.LessonSchedule
import app.models.enums.LessonType
import app.models.enums.WeekDay
import app.repositories.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.sql.Date

@Controller
class ScheduleEditController {

    val mapper = jacksonObjectMapper()

    @Autowired
    lateinit var groupRepository: GroupRepository

    @Autowired
    lateinit var lessonScheduleRepository: LessonScheduleRepository

    @Autowired
    lateinit var subjectRepository: SubjectRepository

    @Autowired
    lateinit var teacherRepository: TeacherRepository

    @Autowired
    lateinit var auditoriumRepository: AuditoriumRepository

    @GetMapping("/schedule/edit/{groupId}")
    fun showEditorWithSchedule(model: Model, @PathVariable groupId: Int): String {
        fillModelDefaultData(model, groupId)
        return "lesson_schedule/edit"
    }

    private fun fillModelDefaultData(model: Model, groupId: Int) {
        model["group"] = groupRepository.findById(groupId).get().groupName
        val a = subjectRepository.findAll().toList()
        model["subjects"] = a
        println(mapper.writeValueAsString(a))
        model["subjects_json"] = mapper.writeValueAsString(a)
        model["teachers"] = teacherRepository.findAll()
            .map { mapOf("id" to it.teacherId, "fullName" to "${it.lastName} ${it.firstName} ${it.patronymic}") }
        model["auditoriums"] = auditoriumRepository.findAll()
    }

    @PostMapping("/schedule/edit/{groupId}")
    fun saveLesson(
        @PathVariable groupId: Int,
        @RequestParam teacherId: Int?,
        @RequestParam isOnline: Boolean?,
        @RequestParam lessonNumber: Int?,
        @RequestParam regularity: String?,
        @RequestParam subjectId: Int?,
        @RequestParam auditorium: Int?,
        @RequestParam dayOfTheWeek: String?,
        model: Model
    ): String {
        if (auditorium != null && teacherId != null && isOnline != null && lessonNumber != null && regularity != null && subjectId != null && auditorium != null && dayOfTheWeek != null) {
            val newLesson = LessonSchedule()
            val lessonType = getLessonType(regularity)
            val teacher = teacherRepository.findById(teacherId).get()
            val weekDay = getWeekDay(dayOfTheWeek)
            newLesson.lessonNumber = lessonNumber
            newLesson.lessonLocation = "7 корпус"

            newLesson.teacher = teacher
            newLesson.online = isOnline

            newLesson.lessonType = lessonType
            newLesson.subject = subjectRepository.findById(subjectId).get()
            newLesson.auditorium = auditoriumRepository.findById(auditorium).get()

            newLesson.weekDay = weekDay
            newLesson.dateCreated = Date(System.currentTimeMillis())
            val sameLesson =
                lessonScheduleRepository.findByTeacherAndWeekDayAndLessonNumberAndLessonType(
                    teacher,
                    weekDay,
                    lessonNumber,
                    lessonType
                )
            val currentGroup = groupRepository.findById(groupId).get()
            if (sameLesson!=null){
                sameLesson.groups = sameLesson.groups.plus(currentGroup)
                lessonScheduleRepository.save(sameLesson)
            } else{
                newLesson.groups = listOf(currentGroup)
                lessonScheduleRepository.save(newLesson)
            }
//            val saved = sameLesson ?: lessonScheduleRepository.save(newLesson)
//            println(sameLesson)
//            println(saved)
//            lessonScheduleRepository.save(newLesson)
//            if (sameLesson.get()!=null
//            )
//                lessonScheduleRepository.addLessonSchedule(newLesson)
        }

        fillModelDefaultData(model, groupId)
        return "lesson_schedule/edit"
    }

    private fun getWeekDay(dayOfTheWeek: String): WeekDay? {
        return when (dayOfTheWeek) {
            "monday" -> WeekDay.MONDAY
            "tuesday" -> WeekDay.TUESDAY
            "friday" -> WeekDay.FRIDAY
            "wednesday" -> WeekDay.WEDNESDAY
            "thursday" -> WeekDay.THURSDAY
            "saturday" -> WeekDay.SATURDAY
            else -> throw IllegalAccessException("Wrong day of the week")
        }
    }

    private fun getLessonType(regularity: String): LessonType? {
        return when (regularity) {
            "chis" -> LessonType.NUMERATOR
            "znam" -> LessonType.DENUMERATOR
            "chis_and_znam" -> LessonType.ALL
            else -> throw IllegalAccessException("Wrong regularity")
        }
    }
}