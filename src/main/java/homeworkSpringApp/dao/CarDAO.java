package homeworkSpringApp.dao;

import homeworkSpringApp.model.Car;

import java.util.Optional;

public interface CarDAO {

    void createCar(Car car);
    Optional<Car> findCar(long carId);
    void deleteCar(long carId);
}
