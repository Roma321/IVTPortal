package app.controllers

import app.models.Auditorium
import app.models.LessonSchedule
import app.models.enums.LessonType
import app.repositories.AuditoriumRepository
import app.repositories.LessonScheduleRepository
import app.util.groupBusynessWithKeyExtract
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class AuditoriumBusynessController {
    private val mapper = jacksonObjectMapper()

    @Autowired
    lateinit var lessonScheduleRepository: LessonScheduleRepository

    @Autowired
    lateinit var auditoriumRepository: AuditoriumRepository

    @GetMapping("/busyness/auditoriums")
    fun showAuditoriumsBusyness(model: Model): String {
        val allIds = auditoriumRepository.findAuditoriumNumbers()
        val currentSchedule =
            groupBusynessWithKeyExtract(lessonScheduleRepository.findAll().toList(), allIds, ::getAuditoriumNumber)
        model["items"] = mapper.writeValueAsString(currentSchedule)
        model["auditoriums"] = currentSchedule.keys
        return "auditorium/auditorium_busyness"
    }

    private fun canBeRearranged(to: List<LessonType>?, from: LessonType): Boolean {
        if (to.isNullOrEmpty()) return true
        if (to.size == 1 && LessonType.ALL !in to) {
            if (from == LessonType.DENOMINATOR && to[0] == LessonType.NUMERATOR
                || from == LessonType.NUMERATOR && to[0] == LessonType.DENOMINATOR
                )
                return true
        }
        return false
    }

    @PostMapping("/busyness/auditoriums")
    fun freeAuditory(model: Model, @RequestParam auditorium: String): String {
        val auditoriumForRelease = auditoriumRepository.findByAuditoriumNumber(auditorium)
        val allAuditoriums = auditoriumRepository.findAll()
        val allIds = allAuditoriums.map { it.auditoriumNumber }
        val auditoriumsWithSameLocationAndNotLessComputers =
            allAuditoriums
                .filter {
                    it.lessonLocation.lessonLocationId == auditoriumForRelease.lessonLocation.lessonLocationId
                            && it.computerAmount >= auditoriumForRelease.computerAmount && it.auditoriumNumber != auditoriumForRelease.auditoriumNumber
                }
        var currentSchedule =
            groupBusynessWithKeyExtract(lessonScheduleRepository.findAll().toList(), allIds, ::getAuditoriumNumber)
        val needFreePlacesForStudents =
            getStudentsAmountInfo(auditorium)
        val updates = mutableListOf<LessonAuditoriumUpdate>()
        for (day in needFreePlacesForStudents) {
            for (pairNumber in day.value) {//Для каждой пары, которую надо переставить,
                for (lesson in pairNumber.value) {
                    val freeAuditoriumWithLeastEnoughPlaces = auditoriumsWithSameLocationAndNotLessComputers
                        .filter { auditorium1 ->
                            canBeRearranged(
                                to = currentSchedule[auditorium1.auditoriumNumber]?.get(day.key)?.get(pairNumber.key),
                                from = lesson.regularity
                            )
                        }
                        .minBy {
                            val diff = it.placeAmount - lesson.size
                            if (diff < 0) return@minBy Int.MAX_VALUE
                            return@minBy diff
                        }
                updates.add(LessonAuditoriumUpdate(lesson.id, freeAuditoriumWithLeastEnoughPlaces))
                }

            }
        }

        println(updates)
        for (update in updates){
            val lesson = lessonScheduleRepository.findById(update.lessonId).get()
            lesson.auditorium = update.auditorium
            lessonScheduleRepository.save(lesson)
        }

        currentSchedule =
            groupBusynessWithKeyExtract(lessonScheduleRepository.findAll().toList(), allIds, ::getAuditoriumNumber)
        model["items"] = mapper.writeValueAsString(currentSchedule)
        model["auditoriums"] = currentSchedule.keys
        return "auditorium/auditorium_busyness"
    }

    private fun getStudentsAmountInfo(auditorium: String) =
        lessonScheduleRepository.findByAuditoriumAuditoriumNumber(auditorium).groupBy { it.weekDay }
            .mapValues { weekDayListEntry ->
                weekDayListEntry.value.groupBy { it.lessonNumber }
                    .mapValues { lessonListEntry ->
                        lessonListEntry.value.map { lessonSchedule ->
                            LessonIdToGroupSize(
                                size = lessonListEntry.value.map { it.groups }
                                    .sumOf { groups -> groups.sumOf { it.peopleAmount } },
                                id = lessonListEntry.value[0].lessonId,
                                regularity = lessonSchedule.lessonType
                            )
                        }
                    }
            }

    private fun getAuditoriumNumber(it: LessonSchedule): String? = it.auditorium.auditoriumNumber
}

data class LessonIdToGroupSize(val id: Int, val size: Int, val regularity: LessonType) {
    override fun toString(): String {
        return "LessonIdToGroupSize(id=$id, size=$size, regularity=$regularity)"
    }

}

data class LessonAuditoriumUpdate(val lessonId: Int, val auditorium: Auditorium) {
    override fun toString(): String {
        return "LessonAuditoriumUpdate(lessonId=$lessonId, auditorium=${auditorium.auditoriumNumber}, place=${auditorium.placeAmount})"
    }
}