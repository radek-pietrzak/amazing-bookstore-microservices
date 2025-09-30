package pl.radek.inventoryservice.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.radek.inventoryservice.request.*;
import pl.radek.inventoryservice.response.Response;

import static pl.radek.inventoryservice.controller.API.*;

public interface InventoryApi {

    @GetMapping(path = ISBN, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Response> getPriceAndQuantity(@PathVariable String isbn);

    @PostMapping(path = STOCK_CHECK)
    ResponseEntity<Response> getStockCheck(@RequestBody StockRequest stockRequest);

    @PostMapping(path = INVENTORY_ADD)
    ResponseEntity<Response> addInventory(@RequestBody InventoryRequest inventoryRequest);

    @PutMapping(path = INVENTORY_UPDATE)
    ResponseEntity<Response> updateInventory(@RequestBody InventoryRequest inventoryRequest);

    @PostMapping(path = DECREMENT_STOCK)
    ResponseEntity<Void> decrementStock(@RequestBody QuantityRequest quantityRequest);

    @PostMapping(path = INCREMENT_STOCK)
    ResponseEntity<Void> incrementStock(@RequestBody QuantityRequest quantityRequest);

    @PostMapping(path = RESERVE_STOCK)
    ResponseEntity<Void> reserveStock(@RequestBody ReservationRequest reservationRequest);

    @PostMapping(path = RELEASE_STOCK)
    ResponseEntity<Response> releaseStock(@RequestBody ReleaseRequest releaseRequest);

}
