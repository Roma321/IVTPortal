package app.models;

import app.models.enums.GroupType;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "\"Group\"")
public class Group implements Serializable {

    public Group(Integer id) {
        groupId = id;
    }

    public Group() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "group_name", nullable = false, length = 16, unique = true)
    private String groupName;

    @Column(nullable = false)
    private Integer course;

    @Column(name = "group_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private GroupType groupType;

    @Column(name = "people_amount", nullable = false)
    private Integer peopleAmount;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public Integer getPeopleAmount() {
        return peopleAmount;
    }

    public void setPeopleAmount(Integer peopleAmount) {
        this.peopleAmount = peopleAmount;
    }

    @Override
    public String toString() {
        return groupName;
    }

}
