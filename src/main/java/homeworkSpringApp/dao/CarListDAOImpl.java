package homeworkSpringApp.dao;

import homeworkSpringApp.model.Car;
import homeworkSpringApp.model.CarList;
import homeworkSpringApp.repository.ListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class CarListDAOImpl implements CarListDAO {

    private final ListRepository listRepository;

    @Override
    public void createList(CarList list) {
        listRepository.saveAndFlush(list);
    }

    @Override
    public Optional<CarList> findList(long listId) {
        return listRepository.findById(listId);
    }

    @Override
    public void deleteList(long listId) {
        listRepository.deleteById(listId);
    }

    @Override
    public List<CarList> getLists() {
        return listRepository.findAll();
    }
}
