package raceroom.calculator.repositories;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String server;

    @Column
    private int startTime;

    @Column
    private int time;

    @Column
    private String fuelUsage;

    @Column
    private String tireWear;

    @Column
    private String mechanicalDamage;

    @Column
    private String flagRules;

    @Column
    private String cutRules;

    @Column
    private String mandatoryPitstop;

    @Column
    private String track;

    @Column
    private String trackLayout;

    @Column
    private String eventName;
}
