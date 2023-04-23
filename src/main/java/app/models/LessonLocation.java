package app.models;

import jakarta.persistence.*;

@Entity
@Table
public class LessonLocation {

    public LessonLocation() {}

    public LessonLocation(Integer lessonLocationId) {
        this.lessonLocationId = lessonLocationId;
    }

    public LessonLocation(String lessonLocation) {
        this.lessonLocation = lessonLocation;
    }

    public LessonLocation(Integer lessonLocationId, String lessonLocation) {
        this.lessonLocationId = lessonLocationId;
        this.lessonLocation = lessonLocation;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_location_id")
    private Integer lessonLocationId;

    @Column(name = "lesson_location.mustache", length = 64, unique = true)
    private String lessonLocation;

    public Integer getLessonLocationId() {
        return lessonLocationId;
    }

    public void setLessonLocationId(Integer lessonLocationId) {
        this.lessonLocationId = lessonLocationId;
    }

    public String getLessonLocation() {
        return lessonLocation;
    }

    public void setLessonLocation(String lessonLocation) {
        this.lessonLocation = lessonLocation;
    }

    @Override
    public String toString() {
        return "LessonLocation{" +
                "lessonLocationId=" + lessonLocationId +
                ", lessonLocation='" + lessonLocation + '\'' +
                '}';
    }
}
