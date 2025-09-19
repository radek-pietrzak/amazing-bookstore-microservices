package pl.radek.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import pl.radek.inventoryservice.dto.Dto;
import pl.radek.inventoryservice.entity.BookInventory;
import pl.radek.inventoryservice.repository.InventoryRepository;
import pl.radek.inventoryservice.request.InventoryRequest;
import pl.radek.inventoryservice.request.QuantityRequest;
import pl.radek.inventoryservice.request.StockRequest;
import pl.radek.inventoryservice.response.Response;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final Dto dto;

    public InventoryService(InventoryRepository inventoryRepository, Dto dto) {
        this.inventoryRepository = inventoryRepository;
        this.dto = dto;
    }

    public Response getPriceAndQuantity(String isbn) {
        BookInventory bookInventory = inventoryRepository.findByIsbn(isbn)
                .orElseThrow(() -> new EntityNotFoundException("Inventory for ISBN: " + isbn + " not found"));

        return dto.bookInventoryToPriceAndQuantity(bookInventory);
    }

    public Response getStockCheck(StockRequest stockRequest) {
        return null;
    }

    public Response addInventory(InventoryRequest inventoryRequest) {
        return null;
    }

    public Response updateInventory(InventoryRequest inventoryRequest) {
        return null;
    }

    public Response decrementInventory(QuantityRequest quantityRequest) {
        return null;
    }

    public Response incrementInventory(QuantityRequest quantityRequest) {
        return null;
    }
}
