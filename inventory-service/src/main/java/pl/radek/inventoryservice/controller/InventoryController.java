package pl.radek.inventoryservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.radek.inventoryservice.request.*;
import pl.radek.inventoryservice.response.Response;
import pl.radek.inventoryservice.service.InventoryService;

@RestController
@RequestMapping
public class InventoryController implements InventoryApi {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public ResponseEntity<Response> getPriceAndQuantity(String isbn) {
        Response response = inventoryService.getPriceAndQuantity(isbn);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response> getStockCheck(StockRequest stockRequest) {
        Response response = inventoryService.getStockCheck(stockRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response> addInventory(InventoryRequest inventoryRequest) {
        Response response = inventoryService.addInventory(inventoryRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response> updateInventory(InventoryRequest inventoryRequest) {
        Response response = inventoryService.updateInventory(inventoryRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> decrementStock(QuantityRequest quantityRequest) {
        inventoryService.decrementStock(quantityRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> incrementStock(QuantityRequest quantityRequest) {
        inventoryService.incrementStock(quantityRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> reserveStock(ReservationRequest reservationRequest) {
        inventoryService.reserveStock(reservationRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Response> releaseStock(ReleaseRequest releaseRequest) {
        Response response = inventoryService.releaseStock(releaseRequest);
        return ResponseEntity.ok(response);
    }
}
