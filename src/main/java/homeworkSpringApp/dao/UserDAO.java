package homeworkSpringApp.dao;

import homeworkSpringApp.model.NumberList;
import homeworkSpringApp.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDAO {

    Optional<User> findUser(UUID id);
    void create(User user);
    List<NumberList<Double>> getUserLists(UUID id);
    List<User> findAll();
    void delete(UUID id);
}
