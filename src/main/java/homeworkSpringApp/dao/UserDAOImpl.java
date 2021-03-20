package homeworkSpringApp.dao;

import homeworkSpringApp.model.NumberList;
import homeworkSpringApp.model.User;
import homeworkSpringApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findUser(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public void create(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public List<NumberList<Double>> getUserLists(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        List<NumberList<Double>> list = new ArrayList<>();
        if (optionalUser.isPresent()) {
            list = optionalUser.get().getUserLists();
        }
        return list;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
