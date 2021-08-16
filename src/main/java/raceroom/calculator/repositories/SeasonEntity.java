package raceroom.calculator.repositories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @JsonIgnore
    @OneToMany(mappedBy = "season", fetch = FetchType.LAZY)
    private Set<EventEntity> events;

    @JsonIgnore
    @OneToMany(mappedBy = "season", fetch = FetchType.LAZY)
    private Set<SeasonResultEntity> results;

    public List<SeasonResultEntity> getsortedResults() {
        return this.getResults().stream().sorted(Comparator.comparing(SeasonResultEntity::getSeasonPoints).reversed()).collect(Collectors.toList());
    }

}
