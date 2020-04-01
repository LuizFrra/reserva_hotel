package org.voo.api.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_airplanes")
@NoArgsConstructor
public class Airplane {

    public Airplane(String id, Long seat, FlyCompany flyCompany, City city) {
        this.id = id;
        this.seat = seat;
        this.flyCompany = flyCompany;
        this.city = city;
    }

    @Id
    @Getter
    private String id;

    @Getter
    @Column(nullable = false)
    private Long seat;

    @Getter
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private FlyCompany flyCompany;

    @Getter
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private City city;

    @OneToMany(mappedBy = "airplane", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Getter
    private Set<FlightBook> flightBook = new HashSet<FlightBook>();

    public boolean bookFlight(EMonth month) {
        long numberOfBookInMonth = 0;

        for (FlightBook flightBook : this.flightBook) {
            if(flightBook.getMonth() == month)
                numberOfBookInMonth++;
        }

        if(numberOfBookInMonth < seat) {
            return flightBook.add(new FlightBook((long)0, month, this));
        }

        return false;
    }

    public long getAvaibleSeatsInMonth(EMonth month) {

        long numberOfBookInMonth = 0;
        for(FlightBook flightBook : this.flightBook) {
            if(flightBook.getMonth() == month)
                numberOfBookInMonth++;
        }

        return this.seat - numberOfBookInMonth;
    }
}
