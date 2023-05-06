package app.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class Title {
    @GetMapping("/")
    fun title(model: Model): String{
        return "title"
    }

}