package raceroom.calculator.repositories;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class SeasonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long Id;

    @Column
    private String name;

    @OneToMany(mappedBy = "season")
    private Set<EventEntity> events;

    @OneToMany(mappedBy = "season")
    private Set<SeasonResultEntity> results;

}
