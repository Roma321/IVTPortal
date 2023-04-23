package app.services.resit;

import app.models.Resit;
import app.repositories.ResitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResitServiceImpl implements ResitService {
    private final ResitRepository repository;

    @Autowired
    public ResitServiceImpl(ResitRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Resit addResit(Resit resit) {
        return repository.save(resit);
    }

    @Override
    @Transactional
    public void deleteResit(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void updateResit(Resit resit) {
        repository.save(resit);
    }

    @Override
    public Resit getById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Iterable<Resit> getAll() {
        return repository.findAll();
    }


}
