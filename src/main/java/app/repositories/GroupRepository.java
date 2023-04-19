package app.repositories;

import app.models.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Integer> {
    void deleteGroupByGroupName(String groupName);
}
