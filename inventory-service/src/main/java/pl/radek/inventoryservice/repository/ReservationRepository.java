package pl.radek.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.radek.inventoryservice.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> , JpaSpecificationExecutor<Reservation> {

}
