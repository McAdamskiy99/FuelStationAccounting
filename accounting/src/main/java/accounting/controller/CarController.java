package accounting.controller;

import accounting.entity.Car;
import accounting.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    // Yangi mashina qo'shish (O'zbekcha: Yangi mashinani bazaga kiritish)
    @PostMapping("/add")
    public Car addCar(@RequestBody Car car) {
        return carService.saveCar(car);
    }

    // Barcha mashinalar ro'yxatini olish (O'zbekcha: Barcha mashinalar va ularning holati)
    @GetMapping("/all")
    public List<Car> getAll() {
        return carService.getAllCars();
    }

    // Muayyan ID bo'yicha mashinani topish
    @GetMapping("/{id}")
    public Car getById(@PathVariable Long id) {
        return carService.getCarById(id);
    }
}
