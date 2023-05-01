package app.services.group;

import app.models.Group;
import app.repositories.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository repository;

    public GroupServiceImpl(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public Group addGroup(Group group) {
        return repository.save(group);
    }

    @Override
    public void deleteGroup(Integer id) {
          repository.deleteById(id);
    }

    @Override
    public Group getById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Iterable<Group> getAll() {
        return repository.findAll();
    }
}
