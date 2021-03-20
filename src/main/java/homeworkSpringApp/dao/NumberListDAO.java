package homeworkSpringApp.dao;

import homeworkSpringApp.model.NumberList;
import java.util.Optional;

public interface NumberListDAO {

    void createList(NumberList numberList);
    Optional<NumberList> findList(long listId);
    void deleteList(long listId);
}
