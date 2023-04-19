package app.controllers;

import app.models.Auditorium;
import app.repositories.AuditoriumRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuditoriumController {
    private final AuditoriumRepository repository;

    public AuditoriumController(AuditoriumRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/auditorium/add")
    public Auditorium addAuditorium(@RequestBody Auditorium auditorium) {
        return repository.save(auditorium);
    }

    @PostMapping("/auditorium/add-several")
    public Iterable<Auditorium> addSeveralAuditorium(@RequestBody List<Auditorium> auditoriums) {
        return repository.saveAll(auditoriums);
    }

    @DeleteMapping("/auditorium/delete/{id}")
    public void deleteAuditorium(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    @GetMapping("/auditorium/all")
    public Iterable<Auditorium> getAllAuditorium() {
        return repository.findAll();
    }


}
