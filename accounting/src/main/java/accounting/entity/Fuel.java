package accounting.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "fuel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fuel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Yoqilg'i nomi (masalan: Ai-92, Diesel)
    private String name;

    // 1 litr uchun narxi
    private Double pricePerLiter;

    // Sisternadagi joriy qoldiq (litrda)
    private Double currentStock;

    // Sisternaning umumiy sig'imi (litrda)
    private Double totalCapacity;

    // --- Hisoblanadigan maydon ---

    // Sisternadagi bo'sh joyni hisoblash (O'zbekcha: Bo'sh joy miqdori)
    public Double getAvailableSpace() {
        if (totalCapacity == null || currentStock == null) return 0.0;
        return totalCapacity - currentStock;
    }
}
