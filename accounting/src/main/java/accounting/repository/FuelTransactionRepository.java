package accounting.repository;

import accounting.entity.FuelTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTransactionRepository extends JpaRepository<FuelTransaction, Long> {
    // Tranzaksiyalar tarixini boshqarish uchun metodlar shu yerda bo'ladi
}
