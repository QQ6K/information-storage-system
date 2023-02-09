package ru.task.iss.sales.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.task.iss.sales.services.SalesService;
import ru.task.iss.sales.services.dtos.SaleDto;
import ru.task.iss.sales.services.dtos.SaleUpdateDto;

@RestController
@RequestMapping(path = "/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    @PostMapping
    public SaleDto createItem(
            @RequestBody SaleDto saleDto
    ) {
        return salesService.createSale(saleDto);
    }

    @GetMapping("/{saleId}")
    public SaleDto readItem(
            @PathVariable Long saleId
    ) {
        return salesService.readSale(saleId);
    }

    @PatchMapping("/{saleId}")
    public SaleUpdateDto updateItem(
            @PathVariable Long saleId,
            @RequestBody SaleDto saleDto
    ) {
        return salesService.updateSale(saleId, saleDto);
    }

    @DeleteMapping("/{saleId}")
    public void deleteItem(
            @PathVariable Long saleId
    ) {
        salesService.deleteSale(saleId);
    }
}
