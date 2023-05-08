package app.controllers

import app.models.LessonSchedule
import app.repositories.GroupRepository
import app.repositories.LessonScheduleRepository
import app.util.groupBusynessWithKeyExtract
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class GroupBusynessController {
    private val mapper = jacksonObjectMapper()

    @Autowired
    lateinit var lessonScheduleRepository: LessonScheduleRepository

    @Autowired
    lateinit var groupRepository: GroupRepository


    @GetMapping("/busyness/groups")
    fun showGroupsBusyness(model: Model): String {
        val a = lessonScheduleRepository.findAll().toList()
        val listWithoutMultipleGroups = mutableListOf<LessonSchedule>()
        val allGroups = groupRepository.findGroupNames()
        for (lesson in a) {
            for (group in lesson.groups) {
                val newLesson = lesson.deepCopy()
                newLesson.groups = listOf(group)
                listWithoutMultipleGroups.add(newLesson)
            }
        }
        val currentSchedule = groupBusynessWithKeyExtract(listWithoutMultipleGroups, allGroups, ::getGroupName)
        model["items"] = mapper.writeValueAsString(currentSchedule)
        return "group/group_busyness"
    }

    private fun getGroupName(it: LessonSchedule): String = it.groups[0].groupName

}