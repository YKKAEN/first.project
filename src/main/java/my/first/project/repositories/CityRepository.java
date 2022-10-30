package my.first.project.repositories;

import my.first.project.entities.Category;
import my.first.project.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CityRepository extends JpaRepository<City, Long> {

}
