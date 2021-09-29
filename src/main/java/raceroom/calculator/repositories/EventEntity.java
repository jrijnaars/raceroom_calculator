package raceroom.calculator.repositories;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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

    @ManyToOne
    @JoinColumn (nullable = false)
    private SeasonEntity season;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SessionEntity> sessions = new HashSet<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PlayerResultEntity> playerResults = new HashSet<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EventResultEntity> eventResults = new HashSet<>();

    public List<EventResultEntity> getsortedResults() {
        return this.getEventResults().stream().sorted(Comparator.comparing(EventResultEntity::getEventPoints).reversed()).collect(Collectors.toList());
    }
}
