package pl.radek.inventoryservice.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.radek.inventoryservice.entity.Reservation;
import pl.radek.inventoryservice.mapper.InventoryMapper;
import pl.radek.inventoryservice.repository.ReservationRepository;
import pl.radek.inventoryservice.request.ReleaseRequest;
import pl.radek.inventoryservice.service.InventoryService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class ReservationCleanupScheduler {

    private final ReservationRepository reservationRepository;
    private final InventoryService inventoryService;
    private final InventoryMapper inventoryMapper;

    public ReservationCleanupScheduler(ReservationRepository reservationRepository,
                                       InventoryService inventoryService,
                                       InventoryMapper inventoryMapper) {
        this.reservationRepository = reservationRepository;
        this.inventoryService = inventoryService;
        this.inventoryMapper = inventoryMapper;
    }

    @Scheduled(fixedRateString = "${app.reservation.cleanup-fixed-rate-milis}")
    public void releaseExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();
        log.info("Running a cleanup job. Searching for reservations older than {}", now);

        List<Reservation> expiredReservations = reservationRepository
                .findByStatusAndExpiresAtBefore(Reservation.ReservationStatus.PENDING, now);

        if (expiredReservations.isEmpty()) {
            log.info("No expired reservations found.");
            return;
        }

        log.warn("Found {} expired reservations to release.", expiredReservations.size());

        for (Reservation reservation : expiredReservations) {
            try {
                log.info("Releasing a reservation with UID: {}", reservation.getReservationUid());
                ReleaseRequest releaseRequest = inventoryMapper.toReleaseRequest(reservation.getReservationUid());
                inventoryService.releaseStock(releaseRequest);
            } catch (Exception e) {
                log.error("Error while releasing reservation with UID: {}", reservation.getReservationUid(), e);
            }
        }
    }
}
