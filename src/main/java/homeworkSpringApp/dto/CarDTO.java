package homeworkSpringApp.dto;

import homeworkSpringApp.model.Car;
import homeworkSpringApp.model.CarList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {


    private long id;
    private String name;
    private Double price;
    private Double horsePower;

    public static CarDTO from(Car car) {
        CarDTO dto = new CarDTO();
        dto.setId(car.getId());
        dto.setName(car.getName());
        dto.setPrice(car.getPrice());
        dto.setHorsePower(car.getHorsePower());
        return dto;
    }

    public Car toCar() {
        Car car = new Car();
        car.setId(this.id);
        car.setPrice(this.price);
        car.setHorsePower(this.getHorsePower());
        car.setName(this.name);
        return car;
    }
}
