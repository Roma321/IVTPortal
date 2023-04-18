package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;

@Controller
public class JavaController {
    @GetMapping("/j")
    public String doSmth(Model model){
        model.addAttribute("value","WORD");
        return "j";
    }
}
