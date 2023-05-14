package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmAnimalEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_animals")
@EntityListeners(FarmAnimalEntityListener.class)
public class FarmAnimal extends BaseEntityAudit {

    // eg cow, goat, chicken...
    @Column(name = "name", nullable = false)
    private String name;

    // a brief description of the animals
    @Column(name = "description")
    private String description;

    // the number of animals eg 5 cows
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_id", nullable = false, updatable = false)
    @ToString.Exclude
    private Farm farm;

    @ManyToOne(targetEntity = User.class, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User owner;
}
