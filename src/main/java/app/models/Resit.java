package app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Table
public class Resit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resit_id")
    private Integer resitId;

    @Column(nullable = false)
    private Date date;

    @Column(name = "time_start", nullable = false)
    private Time timeStart;

    @Column(name = "time_duration", nullable = false)
    private Time timeDuration;

    @Column(name = "is_online", nullable = false)
    private Boolean isOnline;

    @Column(name = "people_amount", nullable = false)
    private Integer peopleAmount;

    @Column(name = "computer_amount", nullable = false)
    @ColumnDefault("0")
    private Integer computerAmount;

    @ManyToMany()
    @JoinTable(
            name = "ResitGroup",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "resit_id") }
    )
    List<Group> groups;

    @OneToOne
    private Auditorium auditorium;

    @OneToOne
    private Teacher teacher;

    @OneToOne
    private Subject subject;

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Integer getComputerAmount() {
        return computerAmount;
    }

    public void setComputerAmount(Integer computerAmount) {
        this.computerAmount = computerAmount;
    }

    public Integer getResitId() {
        return resitId;
    }

    public void setResitId(Integer resitId) {
        this.resitId = resitId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public Time getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(Time timeDuration) {
        this.timeDuration = timeDuration;
    }

    @JsonProperty("isOnline")
    public Boolean getOnline() {
        return isOnline;
    }

    @JsonProperty("isOnline")
    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Integer getPeopleAmount() {
        return peopleAmount;
    }

    public void setPeopleAmount(Integer peopleAmount) {
        this.peopleAmount = peopleAmount;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "Resit{" +
                "resitId=" + resitId +
                ", date=" + date +
                ", timeStart=" + timeStart +
                ", timeDuration=" + timeDuration +
                ", isOnline=" + isOnline +
                ", peopleAmount=" + peopleAmount +
                ", groups=" + groups +
                '}';
    }
}
