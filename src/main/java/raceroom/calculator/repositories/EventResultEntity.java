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

    @ManyToOne
    @JoinColumn( nullable = false)
    private EventEntity event;

    @ManyToOne
    @JoinColumn( nullable = false)
    private PlayerEntity player;

    @Column
    private String carName;

    @Column
    private String trackName;

    @Column
    private int eventPoints;
}
