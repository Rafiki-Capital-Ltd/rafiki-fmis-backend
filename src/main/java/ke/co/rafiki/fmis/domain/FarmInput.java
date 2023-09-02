package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmInputEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "farm_inputs")
@EntityListeners(FarmInputEntityListener.class)
public class FarmInput extends BaseEntityAuditOwned {
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;
}
