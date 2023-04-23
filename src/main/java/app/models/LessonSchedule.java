package app.models;

import app.models.enums.LessonType;
import app.models.enums.WeekDay;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table
public class LessonSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Integer lessonId;

    @Column(name = "is_online", nullable = false)
    private Boolean isOnline;

    @Column(name = "week_day", length = 16, nullable = false)
    @Enumerated(EnumType.STRING)
    private WeekDay weekDay;

    @Column(name = "lesson_number", nullable = false)
    private Integer lessonNumber;

    @Column(name = "lesson_type", length = 24, nullable = false)
    @Enumerated(EnumType.STRING)
    private LessonType lessonType;

    @Column(name = "lesson_location", length = 64, nullable = false)
    private String lessonLocation;

    @Column(name = "date_created", nullable = false)
    private java.sql.Date dateCreated;

    @OneToOne(fetch = FetchType.EAGER)
    private Teacher teacher;

    @OneToOne(fetch = FetchType.EAGER)

    private Subject subject;

    @OneToOne(fetch = FetchType.EAGER)
    private Auditorium auditorium;

    @ManyToMany
    private List<Group> groups;

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    @JsonProperty("isOnline")
    public Boolean getOnline() {
        return isOnline;
    }

    @JsonProperty("isOnline")
    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    public Integer getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(Integer lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public void setLessonType(LessonType lessonType) {
        this.lessonType = lessonType;
    }

    public String getLessonLocation() {
        return lessonLocation;
    }

    public void setLessonLocation(String lessonLocation) {
        this.lessonLocation = lessonLocation;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "LessonSchedule{" +
                "lessonId=" + lessonId +
                ", isOnline=" + isOnline +
                ", weekDay=" + weekDay +
                ", lessonNumber=" + lessonNumber +
                ", lessonType=" + lessonType +
                ", lessonLocation='" + lessonLocation + '\'' +
                ", dateCreated=" + dateCreated +
                ", teacher=" + teacher +
                ", subject=" + subject +
                ", auditorium=" + auditorium +
                ", groups=" + groups +
                '}';
    }
}
