package homeworkSpringApp.repository;

import homeworkSpringApp.model.NumberList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<NumberList<Double>,Long> {
}
