package pl.radek.inventoryservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.radek.events.OrderPlacedEvent;
import pl.radek.inventoryservice.entity.Reservation;
import pl.radek.inventoryservice.repository.ReservationRepository;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventListener {

    private final ReservationRepository reservationRepository;

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleOrderPlacedEvent(OrderPlacedEvent event) {
        log.info("Received order placed event for order ID: {} and reservation UID: {}",
                event.getOrderId(), event.getReservationUid());

        try {
            UUID reservationUid = UUID.fromString(event.getReservationUid());
            Reservation reservation = reservationRepository.findByReservationUid(reservationUid);

            if (reservation == null) {
                log.error("CRITICAL: Received confirmation for non-existent reservation UID: {}", reservationUid);
                // TODO implement in case of non existent reservation
                return;
            }

            if (reservation.getStatus() == Reservation.ReservationStatus.PENDING) {
                reservation.setStatus(Reservation.ReservationStatus.CONFIRMED);
                reservationRepository.save(reservation);
                log.info("Reservation with UID: {} has been confirmed.", reservationUid);
            } else {
                log.warn("Reservation with UID: {} was already in status {} - no action taken.",
                        reservationUid, reservation.getStatus());
            }

        } catch (IllegalArgumentException e) {
            log.error("Invalid reservation UID format received in event: {}", event.getReservationUid(), e);
        } catch (Exception e) {
            log.error("Failed to process order placed event for reservation UID: {}", event.getReservationUid(), e);
        }
    }
}