package accounting.service;

import accounting.entity.Car;
import accounting.entity.Fuel;
import accounting.entity.FuelTransaction;
import accounting.repository.CarRepository;
import accounting.repository.FuelRepository;
import accounting.repository.FuelTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FuelTransactionService {

    private final FuelTransactionRepository transactionRepository;
    private final CarRepository carRepository;
    private final FuelRepository fuelRepository;

    // Barcha tranzaksiyalar tarixini olish
    public List<FuelTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional // Tranzaksiya xavfsizligini ta'minlash uchun (yo hammasi saqlanadi, yo hech narsa)
    public String performRefuel(Long carId, Double amount, Long currentOdometer) {

        // 1. Mashinani bazadan olish
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Mashina topilmadi!"));

        // Agar quyilayotgan miqdor mashina bakidan katta bo'lsa
        if (amount > car.getTankCapacity()) {
            return "Xatolik: Quyilayotgan miqdor (" + amount + " l) mashina baki sig'imidan ("
                    + car.getTankCapacity() + " l) ko'p!";
        }

        // 2. Mashinaga biriktirilgan yoqilg'ini olish
        Fuel fuel = car.getCarModel().getFuel(); // Mashina modeliga mos yoqilg'ini aniqlaymiz

        // Agar modelga yoqilg'i umuman biriktirilmagan bo'lsa
        if (fuel == null) {
            return "Xatolik: Ushbu mashina modeliga yoqilg'i turi biriktirilmagan!";
        }

        // 3. Agar yoqilg'i turi bor, lekin punktda (omborda) qoldiq yetarli bo'lmasa
        if (fuel.getCurrentStock() < amount) {
            return "Xatolik: Omborda ushbu model uchun yetarli " + fuel.getName() + " yo'q! " +
                    "Hozirgi qoldiq: " + fuel.getCurrentStock() + " litr.";
        }


        // 4. Yillik limitni tekshirish
        if (currentOdometer - car.getOdometerStartYear() > car.getCarModel().getAnnualLimit()) {
            return "Xatolik: Ushbu model uchun belgilangan yillik bosib o‘tadigan masofa limiti oshib ketgan!";
        }


        // 5. Tranzaksiyani saqlash
        FuelTransaction transaction = new FuelTransaction();
        transaction.setCar(car);
        transaction.setFuelAmount(amount);
        transaction.setOdometerAtRefill(currentOdometer);
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);

        // 6. Mashina ma'lumotlarini yangilash
        car.setFuelSpentYtd(car.getFuelSpentYtd() + amount);
        car.setOdometerCurrent(currentOdometer);
        carRepository.save(car);

        // 7. Ombor qoldig'ini kamaytirish
        fuel.setCurrentStock(fuel.getCurrentStock() - amount);
        fuelRepository.save(fuel);

        return "Muvaffaqiyatli! Mashina: " + car.getPlateNumber() +
                ", Sarf: " + String.format("%.2f", car.getAverageConsumption()) + " l/100km";
    }
}
