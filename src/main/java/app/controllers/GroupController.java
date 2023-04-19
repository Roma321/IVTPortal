package app.controllers;

import app.models.Group;
import app.repositories.GroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {
    private final GroupRepository repository;

    public GroupController(GroupRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/group/add")
    public Group addGroup(@RequestBody Group group) {
        return repository.save(group);
    }

    @PostMapping("/group/add-several")
    public Iterable<Group> addSeveralGroups(@RequestBody List<Group> groups) {
        return repository.saveAll(groups);
    }

    @DeleteMapping("/group/delete")
    @Transactional
    public void deleteGroup(@RequestParam String name) {
        repository.deleteGroupByGroupName(name);
    }

    @GetMapping("/group/all")
    public Iterable<Group> getAllGroups() {
        return repository.findAll();
    }


}
