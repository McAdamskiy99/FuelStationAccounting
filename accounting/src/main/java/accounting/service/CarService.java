package accounting.service;

import accounting.entity.Car;
import accounting.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    // Yangi mashinani bazaga saqlash
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    // Barcha mashinalar ro'yxatini olish
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // Berilgan id dagi mashinani ko‘rsatadi
    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mashina topilmadi!"));
    }

}
