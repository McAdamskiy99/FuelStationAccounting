package accounting.controller;

import accounting.service.FuelTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class FuelTransactionController {

    private final FuelTransactionService transactionService;

    // Yoqilg'i quyish uchun POST so'rovi
    @PostMapping("/refuel")
    public String refuel(@RequestParam Long carId,
                         @RequestParam Double amount,
                         @RequestParam Long odometer) {
        return transactionService.performRefuel(carId, amount, odometer);
    }
}
