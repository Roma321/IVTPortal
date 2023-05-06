package app.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class SchedulePlan {
    @Id
    @Column(name = "plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer planId;

    @OneToMany
    private List<Group> group;

    @OneToOne
    private Subject subject;

    @Column(name = "hours_amount", nullable = false)
    private Integer hoursAmount;
}
