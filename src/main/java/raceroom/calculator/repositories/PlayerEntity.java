package raceroom.calculator.repositories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PlayerEntity {

    @Id
    @Column
    private Long id;

    @Column
    private String fullName;

    @Column
    private String userName;

}
