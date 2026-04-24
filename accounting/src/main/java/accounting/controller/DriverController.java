package accounting.controller;

import accounting.entity.Driver;
import accounting.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @PostMapping("/add")
    public Driver addDriver(@RequestBody Driver driver) {
        return driverService.saveDriver(driver);
    }

    @GetMapping("/all")
    public List<Driver> getAll() {
        return driverService.getAllDrivers();
    }
}
