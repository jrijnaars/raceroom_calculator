package raceroom.calculator.repositories;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PlayerResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private long eventId;

    @Column
    private String sessionType;

    @ManyToOne
    @JoinColumn (nullable = false)
    private PlayerEntity player;

    @Column
    private int userWeightPenalty;

    @Column
    private int carId;

    @Column
    private String car;

    @Column
    private int carWeightPenalty;

    @Column
    private int position;

    @Column
    private int positionInClass;

    @Column
    private int startPosition;

    @Column
    private int startPositionInClass;

    @Column
    private int bestLapTime;

    @Column
    private String finishStatus;

    @Column
    private int points;

    @Column
    private boolean fastestLap;

    @Column
    private int incidentPoints;
}
