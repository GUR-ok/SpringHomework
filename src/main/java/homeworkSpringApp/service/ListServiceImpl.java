package homeworkSpringApp.service;

import homeworkSpringApp.dao.CarDAO;
import homeworkSpringApp.dao.CarListDAO;
import homeworkSpringApp.dao.UserDAO;
import homeworkSpringApp.dto.CarDTO;
import homeworkSpringApp.dto.CarListDTO;
import homeworkSpringApp.model.Car;
import homeworkSpringApp.model.CarList;
import homeworkSpringApp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListServiceImpl implements ListService{

    private final UserDAO userDAO;
    private final CarListDAO carListDAO;
    private final CarDAO carDAO;

    @Override
    public void createUser(User user) {
        userDAO.create(user);
    }

    @Override
    public void deleteUser(UUID userUUID) {
        userDAO.delete(userUUID);
    }

    @Override
    public Optional<User> getUser(UUID userUUID) {
        return userDAO.findUser(userUUID);
    }

    @Override
    public User getUserByName(String name) {
        return userDAO.findByName(name).get();
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public ResponseEntity<List<CarListDTO>> getLists() {
        List<CarListDTO> result = carListDAO.getLists()
                .stream()
                .map(CarListDTO::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<CarListDTO> getCarList(long listId) {

        if (carListDAO.findList(listId).isPresent())
            return ResponseEntity.ok(CarListDTO.from(carListDAO.findList(listId).get()));
        else
            return ResponseEntity.notFound().build();
    }

    @Override
    public void addCarList(CarList list) {
            carListDAO.createList(list);
    }

    @Override
    public void deleteList(long listId) {
        carListDAO.deleteList(listId);
    }

    @Override
    public ResponseEntity<CarDTO> addCarToList(long id, Car car) {
        if (carListDAO.findList(id).isPresent()) {
            carListDAO.findList(id).get().getCarList().add(car);
            return ResponseEntity.ok(CarDTO.from(car));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity deleteEelementFromList(long id, long id_element) {
        if ((carListDAO.findList(id).isPresent()) && carDAO.findCar(id_element).isPresent()) {
            CarList carList = carListDAO.findList(id).get();
            Car car = carDAO.findCar(id_element).get();
            carList.getCarList().remove(car);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity getCarFromList(long id, long id_element) {
        if ((carListDAO.findList(id).isPresent()) && carDAO.findCar(id_element).isPresent()) {
            CarList carList = carListDAO.findList(id).get();
            Car car = carDAO.findCar(id_element).get();
            if (carList.getCarList().contains(car))
                return ResponseEntity.ok(car);
            else return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity countElement(long id, Car car) {
        if (carListDAO.findList(id).isPresent()) {
            Integer counter = 0;
            for (Car carelem : carListDAO.findList(id).get().getCarList())
            {
                if (carelem.equals(car)) counter++;
            }
            return ResponseEntity.ok(counter);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<String> addListToList(long id, CarList list) {
        if (carListDAO.findList(id).isPresent()) {
            CarList carList = carListDAO.findList(id).get();
            List<Car> addedList = list.getCarList();
            carList.getCarList().addAll(addedList);
            return ResponseEntity.ok(list.getCarList().size()+" elements added to list");
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity getListSize(long id) {
        if (carListDAO.findList(id).isPresent()) {
            CarList carList = carListDAO.findList(id).get();
            Integer size = carList.getCarList().size();
            return ResponseEntity.ok(size);
        }
        return ResponseEntity.notFound().build();
    }


}
