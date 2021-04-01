package homeworkSpringApp.service;

import homeworkSpringApp.dto.CarDTO;
import homeworkSpringApp.dto.CarListDTO;
import homeworkSpringApp.model.Car;
import homeworkSpringApp.model.CarList;
import homeworkSpringApp.model.User;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ListService {
    void createUser(User user);
    void deleteUser(UUID userUUID);
    Optional<User> getUser(UUID userUUID);
    User getUserByName(String name);
    List<User> getAllUsers();

    ResponseEntity<List<CarListDTO>> getLists(UUID uuid);
    ResponseEntity<String> getCarList(long listId, UUID uuid);
    void addCarList(CarList list, UUID uuid);
    void deleteList(long listId, UUID uuid);
    ResponseEntity<CarDTO> addCarToList(long id, Car car, UUID uuid);
    ResponseEntity deleteEelementFromList(long id, long id_element, UUID uuid);
    ResponseEntity<CarDTO> getCarFromList(long id, long id_element, UUID uuid);
    ResponseEntity<Integer> countElement(long id, String carjson, UUID uuid) throws ParseException;
    ResponseEntity<String> addListToList(long id, CarList list, UUID uuid);
    ResponseEntity<Integer> getListSize(long id, UUID uuid);
}
