package pl.radek.inventoryservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.radek.inventoryservice.request.InventoryRequest;
import pl.radek.inventoryservice.request.QuantityRequest;
import pl.radek.inventoryservice.request.StockRequest;
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
    public ResponseEntity<Response> getISBN(String isbn) {
        Response response = inventoryService.getISBN(isbn);
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
    public ResponseEntity<Response> decrementInventory(QuantityRequest quantityRequest) {
        Response response = inventoryService.decrementInventory(quantityRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response> incrementInventory(QuantityRequest quantityRequest) {
        Response response = inventoryService.incrementInventory(quantityRequest);
        return ResponseEntity.ok(response);
    }
}
