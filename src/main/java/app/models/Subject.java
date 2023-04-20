package app.models;

import jakarta.persistence.*;

@Entity
@Table
public class Subject {

    public Subject() {}

    public Subject(Integer subjectId) {
        this.subjectId = subjectId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Integer subjectId;

    @Column(name = "subject_name", length = 128, nullable = false, unique = true)
    private String subjectName;

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
