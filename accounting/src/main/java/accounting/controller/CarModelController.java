package accounting.controller;

import accounting.entity.CarModel;
import accounting.service.CarModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car/models")
@RequiredArgsConstructor
public class CarModelController {
    private final CarModelService carModelService;

    @PostMapping("/add")
    public CarModel add(@RequestBody CarModel model) {
        return carModelService.saveModel(model);
    }

    @GetMapping("/all")
    public List<CarModel> getAll() {
        return carModelService.getAllModels();
    }
}
