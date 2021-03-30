package homeworkSpringApp.dao;

import homeworkSpringApp.model.CarList;
import homeworkSpringApp.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDAO {

    Optional<User> findUser(UUID id);
    Optional<User> findByName(String name);
    void create(User user);
    List<User> findAll();
    void delete(UUID id);
}
