package accounting.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    // Model nomi (masalan: Isuzu, Cobalt)
    @Column(unique = true, nullable = false)
    private String name;

    // Mashina turi (Enum: LIGHT yoki HEAVY)
    @Enumerated(EnumType.STRING)
    private CarType type;

    // Ushbu model uchun belgilangan yillik bosib o‘tadigan masofa limiti (kilometrda)
    private Double annualLimit;

    // 100 km uchun belgilangan sarf me'yori (litrda)
    // Masalan: 12.5 (100 km ga 12.5 litr sarflashi kerak)
    private Double fuelNorm;

    @ManyToOne
    @JoinColumn(name = "fuel_id")
    private Fuel fuel;

    // --- JSON uchun maxsus metodlar ---

    @JsonGetter("fuel") // O'zbekcha: JSON javobida "fuel" nomi bilan faqat nomini chiqaramiz
    public String getFuelName() {
        return (fuel != null) ? fuel.getName() : null;
    }

    @JsonProperty("fuel")
    public void setFuelById(Long id) {
        if (id != null) {
            this.fuel = new Fuel();
            this.fuel.setId(id);
        }
    }

}
