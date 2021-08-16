package raceroom.calculator.repositories;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String type;

    @ManyToOne
    @JoinColumn(nullable = false)
    private EventEntity event;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private Set<PlayerResultEntity> playerResults = new HashSet<>();

}
