package raceroom.calculator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String seasonName;

    @Column
    private String driver;

    @Column
    private int seasonPoints;

    @Column
    private String carname;
}
