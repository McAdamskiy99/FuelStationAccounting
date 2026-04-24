package accounting.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "car_models")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Model nomi (masalan: MAN TGX, Isuzu, Cobalt)
    @Column(unique = true, nullable = false)
    private String name;

    // Mashina turi (Enum: LIGHT yoki HEAVY)
    @Enumerated(EnumType.STRING)
    private CarType type;

    @ManyToOne
    @JoinColumn(name = "fuel_id")
    private Fuel fuel;

    // Ushbu model uchun belgilangan yillik yoqilg'i limiti (litrda)
    private Double annualLimit;

    // 100 km uchun belgilangan sarf me'yori (litrda)
    // Masalan: 12.5 (100 km ga 12.5 litr sarflashi kerak)
    private Double fuelNorm;
}
