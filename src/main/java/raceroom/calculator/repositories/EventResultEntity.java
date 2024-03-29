package raceroom.calculator.repositories;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class EventResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long eventId;

    @Column
    private String eventName;

    @Column
    private String driverName;

    @Column
    private String carName;

    @Column
    private String trackName;

    @Column
    private int eventPoints;
}
