package accounting.repository;

import accounting.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    // Здесь уже есть методы save(), findAll(), findById(), delete()
}
