package pl.radek.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.radek.inventoryservice.entity.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer>, JpaSpecificationExecutor<Reservation> {

    List<Reservation> findByStatusAndCreatedAtBefore(Reservation.ReservationStatus reservationStatus, LocalDateTime expirationTime);

    Reservation findByReservationUid(UUID reservationUid);
}
