package accounting.service;

import accounting.entity.CarModel;
import accounting.repository.CarModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarModelService {
    private final CarModelRepository carModelRepository;

    public CarModel saveModel(CarModel model) {
        return carModelRepository.save(model);
    }

    public List<CarModel> getAllModels() {
        return carModelRepository.findAll();
    }
}

