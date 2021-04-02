package homeworkSpringApp.service;

import homeworkSpringApp.dao.CarDAO;
import homeworkSpringApp.dao.CarListDAO;
import homeworkSpringApp.dao.RoleDAO;
import homeworkSpringApp.dao.UserDAO;
import homeworkSpringApp.dto.CarDTO;
import homeworkSpringApp.dto.CarListDTO;
import homeworkSpringApp.model.Car;
import homeworkSpringApp.model.CarList;
import homeworkSpringApp.model.Role;
import homeworkSpringApp.model.User;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListServiceImpl implements ListService{

    private final UserDAO userDAO;
    private final CarListDAO carListDAO;
    private final CarDAO carDAO;
    private final RoleDAO roleDAO;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptpasswordEncoder = new BCryptPasswordEncoder();
        return bCryptpasswordEncoder;
    }


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void createUser(User user) {
        Role roleUser = roleDAO.findByName("ROLE_USER").get();
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    public ResponseEntity<List<CarListDTO>> getLists(UUID uuid) {
        List<CarListDTO> result = carListDAO.getLists()
                .stream()
                .map(CarListDTO::from)
                .collect(Collectors.toList());
        Iterator<CarListDTO> iterator = result.listIterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getOwnerUuid().equals(uuid))
                iterator.remove();
        }
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<String> getCarList(long listId, UUID uuid) {

        if (carListDAO.findList(listId).isPresent() && carListDAO.findList(listId).get().getUser().getUuid().equals(uuid))
            {   String info = CarListDTO.from(carListDAO.findList(listId).get()) + " " + carListDAO.findList(listId).get().getCars();
            return ResponseEntity.ok(info);}
        else
            return ResponseEntity.notFound().build();
    }

    @Override
    @Transactional
    public void addCarList(CarList list, UUID uuid) {
            list.setUser(this.getUser(uuid).get());

            carListDAO.createList(list);
    }

    @Override
    public void deleteList(long listId, UUID uuid) {
        if (carListDAO.findList(listId).get().getUser().getUuid().equals(uuid))
            carListDAO.deleteList(listId);
    }

    @Override
    public ResponseEntity<CarDTO> addCarToList(long id, Car car, UUID uuid) {
        if (carListDAO.findList(id).isPresent() && carListDAO.findList(id).get().getUser().getUuid().equals(uuid)) {
            carListDAO.findList(id).get().getCars().add(car);
            carDAO.createCar(car);
            return ResponseEntity.ok(CarDTO.from(car));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @Transactional
    public ResponseEntity deleteEelementFromList(long id, long id_element, UUID uuid) {
        if ((carListDAO.findList(id).isPresent()) && carDAO.findCar(id_element).isPresent()
        && carListDAO.findList(id).get().getUser().getUuid().equals(uuid)) {
            CarList carList = carListDAO.findList(id).get();
            Car car = carDAO.findCar(id_element).get();
            carList.getCars().remove(car);
            carDAO.deleteCar(id_element);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity getCarFromList(long id, long id_element, UUID uuid) {
        if ((carListDAO.findList(id).isPresent()) && carDAO.findCar(id_element).isPresent()
             && carListDAO.findList(id).get().getUser().getUuid().equals(uuid)) {
            CarList carList = carListDAO.findList(id).get();
            Car car = carDAO.findCar(id_element).get();
            if (carList.getCars().contains(car))
                return ResponseEntity.ok(CarDTO.from(car));
            else return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity countElement(long id, String carjson, UUID uuid) throws ParseException {
        System.out.println(carjson);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(carjson);
        String name = (String) jsonObject.get("name");
        System.out.println("The name is: " + name);
        Double price = Double.parseDouble((String)jsonObject.get("price"));
        System.out.println("The price is: " + price);
        Double horsepower = Double.parseDouble((String)jsonObject.get("horsepower"));
        System.out.println("The horsepower is: " + horsepower);
        Car car = new Car(name,price,horsepower);
        if (carListDAO.findList(id).isPresent() && carListDAO.findList(id).get().getUser().getUuid().equals(uuid)) {
            Integer counter = 0;
            for (Car carelem : carListDAO.findList(id).get().getCars())
            {
                if (carelem.getName().equals(car.getName()) &&
                        carelem.getHorsePower().equals(car.getHorsePower()) &&
                        carelem.getPrice().equals(car.getPrice()))
                    counter++;
            }
            return ResponseEntity.ok(counter);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<String> addListToList(long id, CarList list, UUID uuid) {
        if (carListDAO.findList(id).isPresent() && carListDAO.findList(id).get().getUser().getUuid().equals(uuid)) {
            CarList carList = carListDAO.findList(id).get();
            List<Car> addedList = list.getCars();
            carList.getCars().addAll(addedList);
            carListDAO.createList(carList);
            return ResponseEntity.ok(list.getCars().size()+" elements added to list");
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity getListSize(long id, UUID uuid) {
        if (carListDAO.findList(id).isPresent() && carListDAO.findList(id).get().getUser().getUuid().equals(uuid)) {
            CarList carList = carListDAO.findList(id).get();
            Integer size = carList.getCars().size();
            return ResponseEntity.ok(size);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<String> getSortedList(long listId, String comparatorName, UUID uuid) {
        if (carListDAO.findList(listId).isPresent() && carListDAO.findList(listId).get().getUser().getUuid().equals(uuid))
        {   List<Car> arrayList = new ArrayList<>();
            arrayList = carListDAO.findList(listId).get().getCars();
            switch (comparatorName) {
                case "ByPrice": {
                    Collections.sort(arrayList, new Car.CarComparatorByPrice());
                    break;
                }
                case "ByHorsepower": {
                    Collections.sort(arrayList, new Car.CarComparatorByHorsePower());
                    break;
                }
                default: {
                    Collections.sort(arrayList, new Car.CarComparatorByName());
                    break;
                }
            }
            String info = "Sorted list from CarList "+ listId + " " + arrayList;
            return ResponseEntity.ok(info);
        }
        else
            return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<String> getShuffledList(long listId, UUID uuid) {
        if (carListDAO.findList(listId).isPresent() && carListDAO.findList(listId).get().getUser().getUuid().equals(uuid))
        {   List<Car> arrayList = new ArrayList<>();
            arrayList = carListDAO.findList(listId).get().getCars();
            Collections.shuffle(arrayList);
            String info = "Shuffled list from CarList "+ listId + " " + arrayList;
            return ResponseEntity.ok(info);
        } else
            return ResponseEntity.notFound().build();
    }


}
