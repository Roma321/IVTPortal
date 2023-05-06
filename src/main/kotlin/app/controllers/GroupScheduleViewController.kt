package app.controllers

import app.repositories.GroupRepository
import app.repositories.LessonScheduleRepository
import app.util.getDisplaySchedule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull

@Controller
class GroupScheduleViewController {
    val mapper = jacksonObjectMapper()

    @Autowired
    lateinit var lessonScheduleRepository: LessonScheduleRepository

    @Autowired
    lateinit var groupRepository: GroupRepository

    @GetMapping("/schedule/group/{groupId}")
    fun showEditorWithSchedule(model: Model, @PathVariable groupId: Int): String {
        val currentSchedule = lessonScheduleRepository.findLessonSchedulesByGroupsGroupId(groupId)
        val displaySchedule = getDisplaySchedule(currentSchedule)
        val group = groupRepository.findById(groupId).getOrNull()
        model["group"] = if (group != null) group.groupName else ""
        model["displaySchedule"] = mapper.writeValueAsString(displaySchedule)
        return "lesson_schedule/group_view"
    }
}