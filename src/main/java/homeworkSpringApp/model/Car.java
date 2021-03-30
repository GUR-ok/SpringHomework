package homeworkSpringApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "car_id")
    private long id;

    @Column(name = "car_name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "horsepower")
    private Double horsePower;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "listed_cars",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "userlist_id"))
    private List<CarList> carlist = new ArrayList<>();


    public static class CarComparatorByName implements Comparator<Car> {

        @Override
        public int compare(Car car1, Car car2) {
            return car1.getName().compareTo(car2.getName());
        }
    }

    public static class CarComparatorByPrice implements Comparator<Car> {

        @Override
        public int compare(Car car1, Car car2) {
            return car1.getPrice().compareTo(car2.getPrice());
        }
    }

    public static class CarComparatorByHorsePower implements Comparator<Car> {

        @Override
        public int compare(Car car1, Car car2) {
            return car1.getHorsePower().compareTo(car2.getHorsePower());
        }
    }
}
