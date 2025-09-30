package pl.radek.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.radek.inventoryservice.dto.Dto;
import pl.radek.inventoryservice.entity.BookInventory;
import pl.radek.inventoryservice.entity.Reservation;
import pl.radek.inventoryservice.entity.ReservationItem;
import pl.radek.inventoryservice.exception.InsufficientStockException;
import pl.radek.inventoryservice.repository.InventoryRepository;
import pl.radek.inventoryservice.repository.ReservationRepository;
import pl.radek.inventoryservice.request.*;
import pl.radek.inventoryservice.response.Response;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ReservationRepository reservationRepository;
    private final Dto dto;


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

    @Transactional
    @Retryable(retryFor = {OptimisticLockException.class}, backoff = @Backoff(delay = 100))
    public void decrementStock(QuantityRequest quantityRequest) {
        log.info("decrementStock: {}", quantityRequest.getQuantity());
        String isbn = quantityRequest.getIsbn();
        int quantity = quantityRequest.getQuantity();
        decrementStock(isbn, quantity);
    }


    @Transactional
    @Retryable(retryFor = {OptimisticLockException.class}, backoff = @Backoff(delay = 100))
    public void decrementStock(String isbn, int quantity) {
        try {
            BookInventory bookInventory = inventoryRepository.findByIsbn(isbn)
                    .orElseThrow(() -> new NoSuchElementException("Inventory not found for ISBN: " + isbn));

            if (bookInventory.getQuantity() < quantity) {
                throw new InsufficientStockException("Not enough items in stock for ISBN: " + isbn);
            }

            bookInventory.setQuantity(bookInventory.getQuantity() - quantity);
            inventoryRepository.save(bookInventory);

        } catch (ObjectOptimisticLockingFailureException e) {
            throw new OptimisticLockException("Stock was updated by another transaction. Please try again.");
        }
    }

    @Transactional
    @Retryable(retryFor = {OptimisticLockException.class}, backoff = @Backoff(delay = 100))
    public void incrementStock(QuantityRequest quantityRequest) {
        String isbn = quantityRequest.getIsbn();
        int quantity = quantityRequest.getQuantity();

        try {
            BookInventory bookInventory = inventoryRepository.findByIsbn(isbn)
                    .orElseThrow(() -> new NoSuchElementException("Inventory not found for ISBN: " + isbn));

            bookInventory.setQuantity(bookInventory.getQuantity() + quantity);
            inventoryRepository.save(bookInventory);

        } catch (ObjectOptimisticLockingFailureException e) {
            throw new OptimisticLockException("Stock was updated by another transaction. Please try again.");
        }
    }

    @Transactional
    public void reserveStock(ReservationRequest reservationRequest) {
        Reservation reservation = Reservation.builder()
                .status(Reservation.ReservationStatus.PENDING)
                .build();

        List<ReservationItem> reservationItems = reservationRequest.getItems().stream()
                .map(itemRequest -> {
                    decrementStock(itemRequest.getIsbn(), itemRequest.getQuantity());
                    return ReservationItem.builder()
                            .isbn(itemRequest.getIsbn())
                            .quantity(itemRequest.getQuantity())
                            .reservation(reservation)
                            .build();
                })
                .collect(Collectors.toList());

        reservation.setItems(reservationItems);
        reservationRepository.save(reservation);

    }

    public Response releaseStock(ReleaseRequest releaseRequest) {
        return null;
    }
}
