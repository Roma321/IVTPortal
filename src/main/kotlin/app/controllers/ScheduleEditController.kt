package app.controllers

import app.models.LessonSchedule
import app.models.Teacher
import app.models.enums.LessonType
import app.models.enums.WeekDay
import app.repositories.*
import app.util.getBusinessInfo
import app.util.getDisplaySchedule
import app.util.getTeacherFullName
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
import kotlin.jvm.optionals.getOrNull

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

    @Autowired
    lateinit var lessonLocationRepository: LessonLocationRepository

    @GetMapping("/schedule/edit/{groupId}")
    fun showEditorWithSchedule(model: Model, @PathVariable groupId: Int): String {
        fillModelDefaultData(model, groupId)
        return "lesson_schedule/edit"
    }

    private fun fillModelDefaultData(model: Model, groupId: Int) {
        val group = groupRepository.findById(groupId).getOrNull()
        val currentSchedule = lessonScheduleRepository.findLessonSchedulesByGroupsGroupId(groupId)
        val fullSchedule = lessonScheduleRepository.findAll().toList()
        val displaySchedule = getDisplaySchedule(currentSchedule)
        model["displaySchedule"] = mapper.writeValueAsString(displaySchedule)
        val busynessInfo =
            getBusinessInfo(currentSchedule, fullSchedule)
        model["busynessInfo"] = mapper.writeValueAsString(busynessInfo)
        model["daysOfWeek"] = WeekDay.values().map { mapOf("value" to it, "name" to it.toRussianName()) }
        model["group"] = if (group != null) group.groupName else ""
        model["locations"] = lessonLocationRepository.findAll()
        model["subjects"] = subjectRepository.findAll()
        val teachers = teacherRepository.findAll()
            .map { mapOf("id" to it.teacherId, "fullName" to getTeacherFullName(it)) }
        model["teachers"] = mapper.writeValueAsString(teachers)
        model["auditoriums"] = mapper.writeValueAsString(
            auditoriumRepository.findAll()
                .filter { group == null || it.placeAmount >= group.peopleAmount }) // Просто чтоб не вылетало
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
        if (auditorium != null && teacherId != null && lessonNumber != null && regularity != null && subjectId != null && dayOfTheWeek != null) {
            val lessonType = getLessonType(regularity)
            val teacher = teacherRepository.findById(teacherId).get()
            val weekDay = WeekDay.valueOf(dayOfTheWeek)
            val newLesson =
                buildNewLesson(lessonNumber, teacher, isOnline, lessonType, subjectId, auditorium, weekDay)
            saveNewLesson(teacher, weekDay, lessonNumber, lessonType, groupId, newLesson)
        }
        fillModelDefaultData(model, groupId)
        return "lesson_schedule/edit"
    }

    private fun saveNewLesson(
        teacher: Teacher,
        weekDay: WeekDay,
        lessonNumber: Int,
        lessonType: LessonType,
        groupId: Int,
        newLesson: LessonSchedule
    ) {
        val sameLesson =
            lessonScheduleRepository.findByTeacherAndWeekDayAndLessonNumberAndLessonType(
                teacher,
                weekDay,
                lessonNumber,
                lessonType
            )
        val currentGroup = groupRepository.findById(groupId).get()
        if (sameLesson != null) {
            if (currentGroup.groupId !in sameLesson.groups.map { it.groupId })
                sameLesson.groups = sameLesson.groups.plus(currentGroup)
            lessonScheduleRepository.save(sameLesson)
        } else {
            newLesson.groups = listOf(currentGroup)
            lessonScheduleRepository.save(newLesson)
        }
    }

    private fun buildNewLesson(
        lessonNumber: Int,
        teacher: Teacher,
        isOnline: Boolean?,
        lessonType: LessonType,
        subjectId: Int,
        auditorium: Int,
        weekDay: WeekDay
    ): LessonSchedule {
        val newLesson = LessonSchedule()
        newLesson.lessonNumber = lessonNumber

        newLesson.teacher = teacher
        newLesson.online = isOnline ?: false //по умолчанию приходит null

        newLesson.lessonType = lessonType
        newLesson.subject = subjectRepository.findById(subjectId).get()
        newLesson.auditorium = auditoriumRepository.findById(auditorium).get()

        newLesson.weekDay = weekDay
        newLesson.dateCreated = Date(System.currentTimeMillis())
        return newLesson
    }

    private fun getLessonType(regularity: String): LessonType {
        return when (regularity) {
            "chis" -> LessonType.NUMERATOR
            "znam" -> LessonType.DENOMINATOR
            "chis_and_znam" -> LessonType.ALL
            else -> throw IllegalAccessException("Wrong regularity")
        }
    }
}

data class PairInfo(val busyTeachers: List<Int>, val busyAuditoriums: List<String>)
data class LessonItem(
    val name: String,
    val regularity: LessonType,
    val teacher: String,
    val auditorium: String,
    val isOnline: Boolean,
    val location: String,
) {
    constructor(it: LessonSchedule) : this(
        name = it.subject.subjectName,
        auditorium = it.auditorium.auditoriumNumber,
        isOnline = it.online,
        location = it.auditorium.lessonLocation.lessonLocation,
        regularity = it.lessonType,
        teacher = getTeacherFullName(it.teacher)
    )
}