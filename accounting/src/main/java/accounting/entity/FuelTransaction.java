package accounting.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    @JsonIgnore
    private Car car;

    // Quyilgan yoqilg'i miqdori (litr)
    private Double fuelAmount;

    // Yoqilg'i quyish paytidagi odometr ko'rsatkichi
    private Long odometerAtRefill;

    // Operatsiya vaqti (avtomatik saqlanadi)
    @Column(name = "transaction_date", nullable = false, updatable = false)
    @CreationTimestamp // Aynan shu yerga qo'shing
    private LocalDateTime transactionDate;

    @JsonProperty("car")
    public void setCarById(Long id) {
        if (id != null) {
            this.car = new Car();
            this.car.setId(id);
        }
    }

    @JsonGetter("car")
    public String getCarPlate() {
        return (car != null) ? car.getPlateNumber() : null;
    }

    @JsonGetter("driver")
    public String getDriverName() {
        return (car != null && car.getDriver() != null)
                ? car.getDriver().getFirstName() + " " + car.getDriver().getLastName() : null;
    }


    @JsonGetter("date")
    public String getFormattedDate() {
        if (transactionDate == null) return "Vaqt belgilanmagan";
        return transactionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }


}
