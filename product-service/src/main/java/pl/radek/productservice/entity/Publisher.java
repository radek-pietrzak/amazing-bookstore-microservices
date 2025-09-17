package pl.radek.productservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="publishers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String publisherName;
    private String publisherDescription;

    public Publisher(String publisherName, String publisherDescription) {
        this.publisherName = publisherName;
        this.publisherDescription = publisherDescription;
    }
}
