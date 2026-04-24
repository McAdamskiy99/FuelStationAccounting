package accounting.repository;

import accounting.entity.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Long> {
    // Yoqilg'i turlari bilan ishlash uchun asosiy metodlar
}
