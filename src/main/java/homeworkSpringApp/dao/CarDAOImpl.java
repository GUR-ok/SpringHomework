package homeworkSpringApp.dao;

import homeworkSpringApp.model.Car;
import homeworkSpringApp.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CarDAOImpl implements CarDAO{

    private final CarRepository carRepository;

    @Override
    public void createCar(Car car) {
        carRepository.saveAndFlush(car);
    }

    @Override
    public Optional<Car> findCar(long carId) {
        return carRepository.findById(carId);
    }

    @Override
    public void deleteCar(long carId) {
        carRepository.deleteById(carId);
    }
}
