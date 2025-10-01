package pl.radek.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.radek.inventoryservice.entity.BookInventory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<BookInventory, Long>,
        JpaSpecificationExecutor<BookInventory> {

    Optional<BookInventory> findByIsbn(String isbn);

    List<BookInventory> findByIsbnIn(Collection<String> strings);
}
