package app.services.group;

import app.models.Group;
import org.springframework.stereotype.Service;

public interface GroupService {
    Group addGroup(Group group);
    void deleteGroup(Integer id);

    Group getById(Integer id);
    Iterable<Group> getAll();
}
