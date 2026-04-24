package accounting.controller;

import accounting.entity.Fuel;
import accounting.service.FuelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fuels") // URL manzilini ham soddalashtirdik
@RequiredArgsConstructor
public class FuelController {

    private final FuelService fuelService; // Yangi servis nomi

    @PostMapping("/add")
    public Fuel add(@RequestBody Fuel fuel) {
        return fuelService.createFuel(fuel);
    }

    @GetMapping("/all")
    public List<Fuel> getAll() {
        return fuelService.getAllFuels();
    }

    // Sisternani to'ldirish uchun yangi metod (Supply)
    @PatchMapping("/{id}/replenish")
    public Fuel replenish(@PathVariable Long id, @RequestParam Double amount) {
        return fuelService.replenishStock(id, amount);
    }
}
