package app.services.auditorium;

import app.models.Auditorium;
import app.repositories.AuditoriumRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditoriumServiceImpl implements AuditoriumService {

    private final AuditoriumRepository repository;

    public AuditoriumServiceImpl(AuditoriumRepository repository) {
        this.repository = repository;
    }

    @Override
    public Auditorium addAuditorium(Auditorium auditorium) {
        return repository.save(auditorium);
    }

    @Override
    public void deleteAuditorium(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Auditorium getById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Iterable<Auditorium> getAll() {
        return repository.findAll();
    }


}
