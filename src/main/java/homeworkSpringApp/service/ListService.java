package homeworkSpringApp.service;

import homeworkSpringApp.model.NumberList;
import homeworkSpringApp.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListService {
    void createUser(User user);
    void deleteUser(UUID userUUID);
    Optional<User> getUser(UUID userUUID);
    List<User> getAllUsers();

    List<NumberList> getUserLists(UUID userUUID);
    Optional<NumberList> getNumberList(long listId);
    void addNumberList(NumberList list, UUID personUUID);
    void deleteNumberList(long listId);
}
