package app.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ScheduleController {
    @GetMapping("/title")
    fun title(model: Model): String{
        model["title"] = "Schedule"
        return "title"
    }

}