package accounting.controller;

import accounting.entity.FuelTransaction;
import accounting.service.FuelTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class FuelTransactionController {

    private final FuelTransactionService transactionService;

    // Yoqilg'i quyish uchun POST so'rovi
    @PostMapping("/refuel")
    public String refuel(@RequestBody FuelTransaction transaction) {
        return transactionService.performRefuel(
                transaction.getCar().getId(),
                transaction.getFuelAmount(),
                transaction.getOdometerAtRefill()
        );
    }

    @GetMapping("/history")
    public List<FuelTransaction> getHistory() {
        return transactionService.getAllTransactions();
    }

}
