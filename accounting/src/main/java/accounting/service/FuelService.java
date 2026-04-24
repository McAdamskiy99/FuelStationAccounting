package accounting.service;

import accounting.entity.Fuel;
import accounting.repository.FuelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FuelService {

    private final FuelRepository fuelRepository;

    // Yangi yoqilg'i turini qo'shish (masalan: Ai-92, 10000L sig'im bilan)
    public Fuel createFuel(Fuel fuel) {
        return fuelRepository.save(fuel);
    }

    // Ombordagi yoqilg'i miqdorini oshirish (Benzovoz kelganda)
    public Fuel replenishStock(Long fuelId, Double amount) {
        Fuel fuel = fuelRepository.findById(fuelId)
                .orElseThrow(() -> new RuntimeException("Yoqilg'i turi topilmadi"));

        // Sig'imdan oshib ketmasligini tekshirish
        if (fuel.getCurrentStock() + amount > fuel.getTotalCapacity()) {
            throw new RuntimeException("Xatolik: Sisterna sig'imidan ortiq yoqilg'i sig'maydi!");
        }

        fuel.setCurrentStock(fuel.getCurrentStock() + amount);
        return fuelRepository.save(fuel);
    }

    public List<Fuel> getAllFuels() {
        return fuelRepository.findAll();
    }
}
