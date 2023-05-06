package app.controllers

import app.models.LessonSchedule
import app.models.Teacher
import app.models.enums.LessonType
import app.models.enums.WeekDay
import app.repositories.*
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
        val group = groupRepository.findById(groupId).get()
        val currentSchedule = lessonScheduleRepository.findLessonSchedulesByGroupsGroupId(groupId)
        val fullSchedule = lessonScheduleRepository.findAll().toList()
        val displaySchedule = currentSchedule.groupBy { it.weekDay }.mapValues { groupedByDay ->
            groupedByDay.value.groupBy { it.lessonNumber }
                .mapValues { groupedByNumber -> groupedByNumber.value.map { LessonItem(it) } }
        }
//        val displaySchedule = currentSchedule.map {
//            LessonItem(it)
//        }
        println(displaySchedule)
        model["displaySchedule"] = mapper.writeValueAsString(displaySchedule)
        println("--------------------------------")
        val busynessInfo =
            getBusinessInfo(currentSchedule, fullSchedule)
//        println(busynessInfo)
        model["busynessInfo"] = mapper.writeValueAsString(busynessInfo)
        model["daysOfWeek"] = WeekDay.values().map { mapOf("value" to it, "name" to it.toRussianName()) }
        model["group"] = group.groupName
        model["locations"] = lessonLocationRepository.findAll()
        model["subjects"] = subjectRepository.findAll()
        val teachers = teacherRepository.findAll()
            .map { mapOf("id" to it.teacherId, "fullName" to getTeacherFullName(it)) }
        model["teachers"] = mapper.writeValueAsString(teachers)
        model["auditoriums"] = mapper.writeValueAsString(
            auditoriumRepository.findAll()
                .filter { it.placeAmount >= group.peopleAmount })
    }

    private fun getBusinessInfo(
        currentSchedule: MutableList<LessonSchedule>,
        fullSchedule: List<LessonSchedule>
    ): MutableMap<WeekDay, Map<String, Any>> {
        val groupedGroupSchedule = currentSchedule.groupBy { it.weekDay }
        val groupedFullSchedule = fullSchedule.groupBy { it.weekDay }
        val weeksForDenominator = listOf(
            LessonType.ALL,
            LessonType.DENOMINATOR
        )
        val weeksForNumerator = listOf(
            LessonType.ALL,
            LessonType.NUMERATOR
        )
        val t = WeekDay.values()
            .map { weekDay ->
                mapOf(
                    weekDay to mapOf(
                        "name" to weekDay.toRussianName(),
                        "positionInWeek" to weekDay.ordinal,
                        "chis" to getFreePairs(
                            excludePairs = groupedGroupSchedule[weekDay]?.filter { it.lessonType in weeksForNumerator }
                                ?.map { lesson -> lesson.lessonNumber } ?: listOf(),
                            weeksLooking = weeksForNumerator,
                            fullSchedule = groupedFullSchedule[weekDay] ?: listOf()
                        ),
                        "znam" to getFreePairs(
                            excludePairs = groupedGroupSchedule[weekDay]?.filter { it.lessonType in weeksForDenominator }
                                ?.map { lesson -> lesson.lessonNumber } ?: listOf(),
                            weeksLooking = weeksForDenominator,
                            fullSchedule = groupedFullSchedule[weekDay] ?: listOf()
                        ),
                        "chis_and_znam" to getFreePairs(
                            excludePairs = groupedGroupSchedule[weekDay]?.map { lesson -> lesson.lessonNumber }
                                ?: listOf(),
                            weeksLooking = LessonType.values().toList(),
                            fullSchedule = groupedFullSchedule[weekDay] ?: listOf()
                        ),
                    )
                )
            }
        val res = mutableMapOf<WeekDay, Map<String, Any>>()
        for (el in WeekDay.values()) {
            val d = t.find { it.keys.contains(el) }!!.values.toList()[0]
            res[el] = d
        }
        return res
    }

    private fun getFreePairs(
        excludePairs: List<Int>,
        weeksLooking: List<LessonType>,
        fullSchedule: List<LessonSchedule>
    ): MutableMap<Int, PairInfo> {
        val groupedByLessonNumber = fullSchedule.filter {
            it.lessonType in weeksLooking && it.lessonNumber !in excludePairs
        }.groupBy { it.lessonNumber }.mapValues { entry ->
            PairInfo(
                busyAuditoriums = entry.value.map { it.auditorium.auditoriumId },
                busyTeachers = entry.value.map { it.teacher.teacherId })
        }.toMutableMap()

        for (key in 1..5) {
            if (key in excludePairs || key in groupedByLessonNumber.keys) continue
            groupedByLessonNumber[key] = PairInfo(listOf(), listOf())
        }
        return groupedByLessonNumber
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
        @RequestParam locationId: Int?,
        model: Model
    ): String {
//        println(teacherId)
//        println(isOnline)
//        println(lessonNumber)
//        println(regularity)
//        println(subjectId)
//        println(auditorium)
//        println(dayOfTheWeek)
//        println(locationId)
        if (locationId != null && auditorium != null && teacherId != null && lessonNumber != null && regularity != null && subjectId != null && auditorium != null && dayOfTheWeek != null) {

            val lessonType = getLessonType(regularity)
            val teacher = teacherRepository.findById(teacherId).get()
            val weekDay = WeekDay.valueOf(dayOfTheWeek)
            val newLesson =
                buildNewLesson(lessonNumber, locationId, teacher, isOnline, lessonType, subjectId, auditorium, weekDay)
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
        locationId: Int,
        teacher: Teacher,
        isOnline: Boolean?,
        lessonType: LessonType,
        subjectId: Int,
        auditorium: Int,
        weekDay: WeekDay
    ): LessonSchedule {
        val newLesson = LessonSchedule()
        newLesson.lessonNumber = lessonNumber
        newLesson.lessonLocation = lessonLocationRepository.findById(locationId).get()

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

data class PairInfo(val busyTeachers: List<Int>, val busyAuditoriums: List<Int>)
data class LessonItem(
    val name: String,
    val regularity: LessonType,
    val teacher: String,
    val auditorium: Int,
    val isOnline: Boolean,
    val location: String,
) {
    constructor(it: LessonSchedule) : this(
        name = it.subject.subjectName,
        auditorium = it.auditorium.auditoriumId,
        isOnline = it.online,
        location = it.lessonLocation.lessonLocation,
        regularity = it.lessonType,
        teacher = getTeacherFullName(it.teacher)
    ) {

    }
}