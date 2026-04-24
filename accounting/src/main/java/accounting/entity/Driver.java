package accounting.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Haydovchining ismi
    private String firstName;

    // Haydovchining familiyasi
    private String lastName;

    // Telefon raqami (ixtiyoriy, lekin foydali)
    private String phoneNumber;

    // Haydovchilik guvohnomasi raqami
    private String licenseNumber;
}
