package homeworkSpringApp.dao;

import homeworkSpringApp.model.NumberList;
import homeworkSpringApp.repository.ListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class NumberListDAOImpl implements NumberListDAO {

    private final ListRepository listRepository;

    @Override
    public void createList(NumberList list) {
        listRepository.saveAndFlush(list);
    }

    @Override
    public Optional<NumberList> findList(long listId) {
        return listRepository.findById(listId);
    }

    @Override
    public void deleteList(long listId) {
        listRepository.deleteById(listId);
    }
}
