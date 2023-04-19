package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class JavaController {
    @PostMapping("/j/{id}")
    public String doSmth(Model model, @PathVariable Integer id, @RequestBody String name){
        model.addAttribute("value", id);
        model.addAttribute("name", name);

        return "j";
    }
}
