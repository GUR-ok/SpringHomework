package homeworkSpringApp.dao;

import homeworkSpringApp.model.Car;
import homeworkSpringApp.model.CarList;

import java.util.List;
import java.util.Optional;

public interface CarListDAO {

    void createList(CarList carList);
    Optional<CarList> findList(long listId);
    void deleteList(long listId);
    List<CarList> getLists();
}
