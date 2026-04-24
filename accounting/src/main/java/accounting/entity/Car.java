package accounting.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plate_number", unique = true, nullable = false)
    private String plateNumber;     // Davlat raqami

    @ManyToOne
    @JoinColumn(name = "model_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CarModel carModel;      // Modeli

    private Double tankCapacity;    // Bak sig‘imi (litr)

    private Double fuelSpentYtd;    // Yil boshidan yoqilg‘i sarfi
    private Long odometerStartYear; // Yil boshida odometr ko‘rsatkichi
    private Long odometerCurrent;   // Odometr ko‘rsatkichi

    @ManyToOne
    @JoinColumn(name = "driver_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Driver driver;      // Mashina haydovchisi

    // Bosib o‘tgan masofasi
    public Long getDistanceTraveled() {
        if (odometerCurrent == null || odometerStartYear == null) return 0L;
        return odometerCurrent - odometerStartYear;
    }

    // 100 km ga yoqilg‘ining o‘rtacha sarfi
    public Double getAverageConsumption() {
        Long distance = getDistanceTraveled();
        if (distance <= 0 || fuelSpentYtd == null || fuelSpentYtd == 0) return 0.0;
        return (fuelSpentYtd / distance) * 100;
    }

    // Yillik limitni modeldan olish uchun qulay metod
    public Double getAnnualLimit() {
        return (carModel != null) ? carModel.getAnnualLimit() : 0.0;
    }

    // Real sarfni model me'yori bilan solishtirish
    public Double getConsumptionDeviation() {
        Double realConsumption = getAverageConsumption();
        Double norm = (carModel != null) ? carModel.getFuelNorm() : 0.0;

        if (norm <= 0) return 0.0;

        // Me'yordan farqni foizda hisoblaydi
        // Masalan: +5.0 (me'yordan 5% ko'p sarflayapti)
        return ((realConsumption - norm) / norm) * 100;
    }

    // Mashina turini modeldan olish
    public CarType getType() {
        return (carModel != null) ? carModel.getType() : null;
    }

    // Yoqilg‘i turini oladi
    @JsonIgnore
    public Fuel getFuel() {
        return (carModel != null) ? carModel.getFuel() : null;
    }

    // JSON javobida faqat yoqilg'i nomi chiqishi uchun
    @JsonGetter("fuel")
    public String getFuelName() {
        Fuel fuel = getFuel();
        return (fuel != null) ? fuel.getName() : null;
    }

    // JSON javobida faqat model nomi chiqishi uchun

    @JsonProperty("carModel")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public String getCarModelName() {
        return (carModel != null) ? carModel.getName() : null;
    }

    // JSON javobida faqat haydovchi ismi va familiyasini chiqarishi uchun
    @JsonProperty("driver")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public String getDriverName() {
        return (driver != null) ? driver.getFirstName() + " " + driver.getLastName() : null;
    }

    // --- ID orqali ma'lumot qo'shish uchun ---

    @JsonProperty("carModel")
    public void setCarModelById(Long id) {
        if (id != null) {
            this.carModel = new CarModel();
            this.carModel.setId(id);
        }
    }

    @JsonProperty("driver")
    public void setDriverById(Long id) {
        if (id != null) {
            this.driver = new Driver();
            this.driver.setId(id);
        }
    }
}
