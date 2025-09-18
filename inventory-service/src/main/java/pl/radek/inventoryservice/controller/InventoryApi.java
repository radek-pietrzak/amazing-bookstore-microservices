package pl.radek.inventoryservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.radek.inventoryservice.request.InventoryRequest;
import pl.radek.inventoryservice.request.QuantityRequest;
import pl.radek.inventoryservice.request.StockRequest;
import pl.radek.inventoryservice.response.Response;

import static pl.radek.inventoryservice.controller.API.*;

public interface InventoryApi {

    @GetMapping(path = ISBN)
    ResponseEntity<Response> getISBN(@PathVariable String isbn);

    @PostMapping(path = STOCK_CHECK)
    ResponseEntity<Response> getStockCheck(@RequestBody StockRequest stockRequest);

    @PostMapping(path = INVENTORY_ADD)
    ResponseEntity<Response> addInventory(@RequestBody InventoryRequest inventoryRequest);

    @PutMapping(path = INVENTORY_UPDATE)
    ResponseEntity<Response> updateInventory(@RequestBody InventoryRequest inventoryRequest);

    @PostMapping(path = INVENTORY_DECREMENT)
    ResponseEntity<Response> decrementInventory(@RequestBody QuantityRequest quantityRequest);

    @PostMapping(path = INVENTORY_INCREMENT)
    ResponseEntity<Response> incrementInventory(@RequestBody QuantityRequest quantityRequest);

}
