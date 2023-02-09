package ru.task.iss.sales.services;

import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.models.Sale;
import ru.task.iss.sales.services.dtos.SaleDto;
import ru.task.iss.sales.services.dtos.SaleUpdateDto;

public interface SalesService {
    @Transactional
    SaleDto createSale(SaleDto saleDto);

    SaleDto readSale(Long saleId);

    @Transactional
    SaleUpdateDto updateSale(Long saleId, SaleDto saleDto);

    @Transactional
    void deleteSale(Long saleId);

    Sale findSaleInRepository(Long saleId);
}
