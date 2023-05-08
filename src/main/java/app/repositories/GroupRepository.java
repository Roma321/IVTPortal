package app.repositories;

import app.models.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Integer> {
    void deleteGroupByGroupName(String groupName);

    Group findGroupByGroupName(String groupName);

    @Query("SELECT groupName FROM Group")
    List<String> findGroupNames();

}
