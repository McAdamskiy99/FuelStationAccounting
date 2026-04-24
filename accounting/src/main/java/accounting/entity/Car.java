package accounting.entity;

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
    private CarModel carModel;      // Modeli

    private Double tankCapacity;    // Bak sig‘imi (litr)
    private Double annualLimit;

    private Double fuelSpentYtd;    // Yil boshidan yoqilg‘i sarfi
    private Long odometerStartYear; // Yil boshida odometr ko‘rsatkichi
    private Long odometerCurrent;   // Odometr ko‘rsatkichi

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driverName;      // Mashina haydovchisi

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
        Double realConsumption = getAverageConsumption(); // Biz avval yozgan real sarf metodi
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
    public Fuel getFuel() {
        return (carModel != null) ? carModel.getFuel() : null;
    }
}
