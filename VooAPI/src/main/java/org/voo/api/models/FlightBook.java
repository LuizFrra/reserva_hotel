package org.voo.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_flightsbooks")
@NoArgsConstructor
@AllArgsConstructor
public class FlightBook {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private long id;

    @Column(nullable = false)
    @Getter
    private EMonth month;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Airplane airplane;
}
