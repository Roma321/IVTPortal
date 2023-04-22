package app.controllers;

import app.models.Resit;
import app.models.Teacher;
import app.services.resit.ResitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Controller
public class ResitController {
    private final ResitService resitService;

    @Autowired
    public ResitController(ResitService resitService) {
        this.resitService = resitService;
    }

    @PostMapping("/resit/add")
    public String addResit(@RequestParam Map<String, Object> map) {

        return "redirect:/resit/add";
    }
    @GetMapping("/resit/add")
    public String addResit() {
        //Нужен сервис для teachers, auditors, subjects, чтобы их в combobox показывать
        //Iterable<Teacher> teachers = teacherService.getAll()
        //model.addAttribute("teachers", teachers);
        //....
        return "resit/add";
    }
    @PostMapping("/resit/delete/{id}")
    public String deleteResit(@PathVariable Integer id) {
        resitService.deleteResit(id);
        return "redirect:/resit/all";
    }

    @GetMapping("/resit/all")
    public String getAll(Model model) {
        Iterable<Resit> resits = resitService.getAll();
        model.addAttribute("resits", resits);
        return "resit/resit";
    }
}
