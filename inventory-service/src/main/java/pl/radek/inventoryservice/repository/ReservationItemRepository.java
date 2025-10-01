package pl.radek.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.radek.inventoryservice.entity.ReservationItem;

import java.util.List;

@Repository
public interface ReservationItemRepository extends JpaRepository<ReservationItem, Integer> , JpaSpecificationExecutor<ReservationItem> {

    List<ReservationItem> findByReservationId(Long reservationId);
}
