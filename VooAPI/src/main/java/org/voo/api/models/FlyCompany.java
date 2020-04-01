package org.voo.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "tbl_flycompanies")
public class FlyCompany {

    @Getter
    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Column(unique = true, nullable = false, length = 50, columnDefinition = "varchar(50)")
    private String name;

    @OneToMany(mappedBy = "flyCompany", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Airplane> airplane = new HashSet<Airplane>();

    public FlyCompany(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean addAirPlane(String airplaneId, Long numberOfSeats, City city) {

        if(numberOfSeats <= 0)
            return false;

        for(Airplane airplane : this.airplane) {
            if(airplane.getId() == airplaneId)
                return false;
        }
        return this.airplane.add(new Airplane(airplaneId, numberOfSeats, this, city));
    }

    public List<Airplane> getAirplanesByCity(long cityId) {
        List<Airplane> airplanes = new ArrayList<Airplane>();
        for(Airplane airP : this.airplane){
            if(airP.getCity().getId() == cityId)
                airplanes.add(airP);
        }
        return airplanes;
    }

    public Airplane getAirPlaneById(String airplaneId) {
        for(Airplane airplane : this.airplane) {
            if(airplaneId.equals(airplane.getId()))
                return airplane;
        }

        return null;
    }
}
