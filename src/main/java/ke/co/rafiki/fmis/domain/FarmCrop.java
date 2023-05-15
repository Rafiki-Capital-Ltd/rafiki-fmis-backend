package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmCropEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_crops")
@EntityListeners(FarmCropEntityListener.class)
public class FarmCrop extends BaseEntityAuditOwned {
    // the name of the crop eg maize, beans, mangoes
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    // the size in acres of the crop planted on the farm
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_id", nullable = false, updatable = false)
    @ToString.Exclude
    private Farm farm;
}
