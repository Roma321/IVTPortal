package app.controllers;

import app.models.*;
import app.services.auditorium.AuditoriumService;
import app.services.group.GroupService;
import app.services.resit.ResitService;
import app.services.subject.SubjectService;
import app.services.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ResitController {
    private final ResitService resitService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final AuditoriumService auditoriumService;
    private final GroupService groupService;

    @Autowired
    public ResitController(ResitService resitService, TeacherService teacherService, SubjectService subjectService,
                           AuditoriumService auditoriumService, GroupService groupService) {
        this.resitService = resitService;
        this.teacherService = teacherService;
        this.subjectService = subjectService;
        this.auditoriumService = auditoriumService;
        this.groupService = groupService;
    }
    @PostMapping("/resit/add")
    public String addResit(@RequestParam (value = "resitId", required = false) Integer id,
                           @RequestParam (value = "groupId", required = false)
                               Integer groupId,
                           @RequestParam (value = "date", required = false)
                           @DateTimeFormat(pattern = "yyyy-MM-dd")
                               LocalDate date,
                           @RequestParam (value = "timeStart", required = false)
                           @DateTimeFormat(pattern = "hh:mm:ss")
                               LocalTime timeStart,
                           @RequestParam (value = "timeDuration", required = false)
                           @DateTimeFormat(pattern = "hh:mm:ss")
                               LocalTime timeDuration,
                           @RequestParam (value = "teacherId", required = false)
                               Integer teacherId,
                           @RequestParam (value = "auditoriumNumber", required = false)
                               Integer auditoriumNumber,
                           @RequestParam (value = "subjectId", required = false)
                               Integer subjectId,
                           @RequestParam (value = "isOnline", required = false)
                               Boolean isOnline,
                           @RequestParam (value = "peopleAmount", required = false)
                               Integer peopleAmount,
                           @RequestParam (value = "computerAmount", required = false)
                               Integer computerAmount
                           ) {

        Resit resit = id == null ? new Resit() : resitService.getById(id);
        List<Group> groupList = new ArrayList<>();
        Group group = groupService.getById(groupId);
        groupList.add(group);
        Teacher resitTeacher = teacherService.getById(teacherId);
        Auditorium resitAuditorium = auditoriumService.getById(auditoriumNumber);
        Subject resitSubject = subjectService.getById(subjectId);
        resit.setGroups(groupList);
        resit.setDate(date == null ? null : Date.valueOf(date));
        resit.setTimeStart(timeStart == null ? null : Time.valueOf(timeStart));
        resit.setTimeDuration(timeDuration == null ? null : Time.valueOf(timeDuration));
        resit.setTeacher(resitTeacher);
        resit.setAuditorium(resitAuditorium);
        resit.setSubject(resitSubject);
        resit.setOnline(isOnline);
        resit.setPeopleAmount(peopleAmount);
        resit.setComputerAmount(computerAmount);
        if (id == null) {
            resitService.addResit(resit);
        } else {
            resitService.updateResit(resit);
        }
        return "redirect:/resit/all";
    }
    @GetMapping("/resit/add")
    public String addResit(Model model) {
        Iterable<Group> resitGroups = groupService.getAll();
        model.addAttribute("resitGroups", resitGroups);
        Iterable<Teacher> teachers = teacherService.getAll();
        model.addAttribute("teachers", teachers);
        Iterable<Subject> subjects = subjectService.getAll();
        model.addAttribute("subjects", subjects);
        Iterable<Auditorium> auditoriums = auditoriumService.getAll();
        model.addAttribute("auditoriums", auditoriums);
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
    @GetMapping("/resit/edit/{id}")
    public String editResit(Model model, @PathVariable Integer id) {
        Iterable<Group> resitGroups = groupService.getAll();
        model.addAttribute("resitGroups", resitGroups);
        Iterable<Teacher> teachers = teacherService.getAll();
        model.addAttribute("teachers", teachers);
        Iterable<Subject> subjects = subjectService.getAll();
        model.addAttribute("subjects", subjects);
        Iterable<Auditorium> auditoriums = auditoriumService.getAll();
        model.addAttribute("auditoriums", auditoriums);
        Resit resit = resitService.getById(id);
        model.addAttribute("resit", resit);
        Teacher teacher = resit.getTeacher();
        Auditorium auditorium = resit.getAuditorium();
        Subject subject = resit.getSubject();
        model.addAttribute("teacher", teacher);
        model.addAttribute("auditorium", auditorium);
        model.addAttribute("subject", subject);
        return "resit/edit";
    }

}
