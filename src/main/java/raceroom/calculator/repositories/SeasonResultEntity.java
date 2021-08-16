package raceroom.calculator.repositories;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class SeasonResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn (nullable = false)
    private SeasonEntity season;

    @Column
    private String driver;

    @Column
    private int seasonPoints;

    @Column
    private String carname;
}
