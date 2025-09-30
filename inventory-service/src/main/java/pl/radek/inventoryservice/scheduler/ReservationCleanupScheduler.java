package pl.radek.inventoryservice.scheduler;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final int timeoutMinutes;
    private final InventoryMapper inventoryMapper;

    public ReservationCleanupScheduler(ReservationRepository reservationRepository,
                                       InventoryService inventoryService,
                                       @Value("${app.reservation.timeout-minutes}") int timeoutMinutes,
                                       InventoryMapper inventoryMapper) {
        this.reservationRepository = reservationRepository;
        this.inventoryService = inventoryService;
        this.timeoutMinutes = timeoutMinutes;
        this.inventoryMapper = inventoryMapper;
    }

    @Scheduled(fixedRate = 300000)
    public void releaseExpiredReservations() {
        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(timeoutMinutes);
        log.info("Running a cleanup job. Searching for reservations older than {}", expirationTime);

        List<Reservation> expiredReservations = reservationRepository
                .findByStatusAndCreatedAtBefore(Reservation.ReservationStatus.PENDING, expirationTime);

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
