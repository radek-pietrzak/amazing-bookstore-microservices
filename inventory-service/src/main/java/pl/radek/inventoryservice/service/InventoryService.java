package pl.radek.inventoryservice.service;

import org.springframework.stereotype.Service;
import pl.radek.inventoryservice.request.InventoryRequest;
import pl.radek.inventoryservice.request.QuantityRequest;
import pl.radek.inventoryservice.request.StockRequest;
import pl.radek.inventoryservice.response.Response;

@Service
public class InventoryService {

    public InventoryService() {}

    public Response getISBN(String isbn) {
        return null;
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
