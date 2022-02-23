package raceroom.calculator.repositories;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@Entity
public class CarDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String carName;

    @Column
    private String carclass;

    @Column
    private String origin;

    @Column
    private String production_year;

    @Column
    private BigDecimal weight;

    @Column
    private String engine_type;

    @Column
    private String aspiration;

    @Column
    private String drive_type;

    @Column
    private BigDecimal horse_power;

    @Column
    private BigDecimal powerToWeigth;

}
