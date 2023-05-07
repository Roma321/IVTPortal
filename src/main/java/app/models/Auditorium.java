package app.models;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@Table
public class Auditorium implements Serializable {

    public Auditorium() {}

    public Auditorium(Integer auditoriumNumber) {
        this.auditoriumNumber = auditoriumNumber;
    }

    @Id
    @Column(name = "auditorium_id")
    private Integer auditoriumNumber;

    @Column(name = "place_amount", nullable = false)
    private Integer placeAmount;

    @Column(name = "computer_amount")
    @ColumnDefault("0")
    private Integer computerAmount;

    public Integer getAuditoriumNumber() {
        return auditoriumNumber;
    }

    public void setAuditoriumNumber(Integer auditoriumNumber) {
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
