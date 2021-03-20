package homeworkSpringApp.service;

import homeworkSpringApp.dao.NumberListDAO;
import homeworkSpringApp.dao.UserDAO;
import homeworkSpringApp.model.NumberList;
import homeworkSpringApp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListServiceImpl implements ListService{

    private final UserDAO userDAO;
    private final NumberListDAO numberListDAO;

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
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public List<NumberList> getUserLists(UUID userUUID) {
        return userDAO.getUserLists(userUUID);
    }

    @Override
    public Optional<NumberList> getNumberList(long listId) {
        return Optional.empty();
    }

    @Override
    public void addNumberList(NumberList list, UUID userUUID) {
        Optional<User> optionalUser = userDAO.findUser(userUUID);
        if (optionalUser.isPresent()) {
            optionalUser.get().getUserLists().add(list);
            list.setUser(optionalUser.get());
            numberListDAO.createList(list);
        }
    }

    @Override
    public void deleteNumberList(long listId) {

    }
}
