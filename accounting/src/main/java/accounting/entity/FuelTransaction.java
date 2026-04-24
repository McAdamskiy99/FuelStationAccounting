package accounting.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fuel_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mashina bilan bog'liqlik (Har bir tranzaksiya bitta mashinaga tegishli)
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    // Quyilgan yoqilg'i miqdori (litr)
    private Double fuelAmount;

    // Yoqilg'i quyish paytidagi odometr ko'rsatkichi
    private Long odometerAtRefill;

    // Operatsiya vaqti (avtomatik saqlanadi)
    private LocalDateTime transactionDate;
}
