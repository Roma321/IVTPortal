package app.controllers;

import app.models.Resit;
import app.services.resit.ResitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ResitController {
    private final ResitService resitService;

    public ResitController(ResitService resitService) {
        this.resitService = resitService;
    }

    @PostMapping("/resit/add")
    public String addResit(Model model, @RequestBody Resit resit) {
        resitService.addResit(resit);

        model.addAttribute("date", resit.getDate().toLocalDate());
        model.addAttribute("time", resit.getTimeStart().toLocalTime());
        model.addAttribute("isOnline", resit.getOnline() ? "Онлайн" : "В корпусе");

        return "resit/resit";
    }

    @DeleteMapping("/resit/delete/{id}")
    public String deleteResit(Model model, @PathVariable Integer id) {
        resitService.deleteResit(id);

        model.addAttribute("date", "Удалено");
        model.addAttribute("time", "Удалено");
        model.addAttribute("isOnline", "Удалено");

        return "resit/resit";
    }

    @GetMapping("/resit/all")
    public Iterable<Resit> getAll(Model model) {
        return resitService.getAll();
    }
}
