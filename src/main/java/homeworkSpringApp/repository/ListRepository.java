package homeworkSpringApp.repository;

import homeworkSpringApp.model.CarList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<CarList,Long> {
}
