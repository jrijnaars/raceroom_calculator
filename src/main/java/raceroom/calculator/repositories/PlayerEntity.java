package raceroom.calculator.repositories;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private long raceId;

    @Column
    private String sessionType;

    @Column
    private int userId;

    @Column
    private String fullName;

    @Column
    private String username;

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
    private String finishStatus;

    @Column
    private int points;
}
