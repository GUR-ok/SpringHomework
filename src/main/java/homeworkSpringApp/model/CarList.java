package homeworkSpringApp.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "carlists")
public class CarList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userlist_id")
    private long id;

    @Column(name = "description")
    private String shortDescription;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "listed_cars",
            joinColumns = @JoinColumn(name = "userlist_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id"))
    private List<Car> cars = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid", nullable = false)
    private User user;
}
