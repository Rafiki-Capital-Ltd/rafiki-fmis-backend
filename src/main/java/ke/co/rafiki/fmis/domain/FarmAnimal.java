package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmAnimalEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_animals")
@EntityListeners(FarmAnimalEntityListener.class)
public class FarmAnimal extends BaseEntityAuditOwned {

    // TODO: Farm animal category

    // eg cow, goat, chicken...
    @Column(name = "type", nullable = false)
    private String type;

    // a brief description of the animals
    @Column(name = "description")
    private String description;

    // the number of animals eg 5 cows
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    @ToString.Exclude
    private Farm farm;
}
