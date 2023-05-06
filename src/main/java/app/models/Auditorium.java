package app.models;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table
public class Auditorium {

    public Auditorium() {}

    public Auditorium(Integer auditoriumId) {
        this.auditoriumId = auditoriumId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auditorium_id")
    private Integer auditoriumId;

    @Column(name = "auditorium_number", nullable = false, length = 16)
    private String auditoriumNumber;

    @Column(name = "place_amount", nullable = false)
    private Integer placeAmount;

    @Column(name = "computer_amount")
    @ColumnDefault("0")
    private Integer computerAmount;

    public Integer getAuditoriumId() {
        return auditoriumId;
    }

    public void setAuditoriumId(Integer auditoriumId) {
        this.auditoriumId = auditoriumId;
    }

    public String getAuditoriumNumber() {
        return auditoriumNumber;
    }

    public void setAuditoriumNumber(String auditoriumNumber) {
        this.auditoriumNumber = auditoriumNumber;
    }

    public Integer getPlaceAmount() {
        return placeAmount;
    }

    public void setPlaceAmount(Integer placeAmount) {
        this.placeAmount = placeAmount;
    }

    public Integer getComputerAmount() {
        return computerAmount;
    }

    public void setComputerAmount(Integer computerAmount) {
        this.computerAmount = computerAmount;
    }
}
