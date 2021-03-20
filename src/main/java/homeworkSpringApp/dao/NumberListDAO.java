package homeworkSpringApp.dao;

import homeworkSpringApp.model.NumberList;
import java.util.Optional;

public interface NumberListDAO {

    void createList(NumberList<Double> numberList);
    Optional<NumberList<Double>> findList(long listId);
    void deleteList(long listId);
}
