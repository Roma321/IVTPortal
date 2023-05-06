package app.util

import app.controllers.LessonItem
import app.controllers.PairInfo
import app.models.LessonSchedule
import app.models.enums.LessonType
import app.models.enums.WeekDay


fun getDisplaySchedule(currentSchedule: MutableList<LessonSchedule>) =
    currentSchedule.groupBy { it.weekDay }.mapValues { groupedByDay ->
        groupedByDay.value.groupBy { it.lessonNumber }
            .mapValues { groupedByNumber -> groupedByNumber.value.map { LessonItem(it) } }
    }


fun getBusinessInfo(
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
            busyAuditoriums = entry.value.map { it.auditorium.auditoriumNumber },
            busyTeachers = entry.value.map { it.teacher.teacherId })
    }.toMutableMap()

    for (key in 1..5) {
        if (key in excludePairs || key in groupedByLessonNumber.keys) continue
        groupedByLessonNumber[key] = PairInfo(listOf(), listOf())
    }
    return groupedByLessonNumber
}