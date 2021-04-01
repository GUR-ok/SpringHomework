package homeworkSpringApp.service;

import homeworkSpringApp.dto.CarDTO;
import homeworkSpringApp.dto.CarListDTO;
import homeworkSpringApp.model.Car;
import homeworkSpringApp.model.CarList;
import homeworkSpringApp.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListService {
    void createUser(User user);
    void deleteUser(UUID userUUID);
    Optional<User> getUser(UUID userUUID);
    User getUserByName(String name);
    List<User> getAllUsers();

    ResponseEntity<List<CarListDTO>> getLists(UUID uuid);
    ResponseEntity<CarListDTO> getCarList(long listId);
    void addCarList(CarList list);
    void deleteList(long listId);
    ResponseEntity<CarDTO> addCarToList(long id, Car car);
    ResponseEntity deleteEelementFromList(long id, long id_element);
    ResponseEntity<CarDTO> getCarFromList(long id, long id_element);
    ResponseEntity<Integer> countElement(long id, Car car);
    ResponseEntity<String> addListToList(long id, CarList list);
    ResponseEntity<Integer> getListSize(long id);
}
