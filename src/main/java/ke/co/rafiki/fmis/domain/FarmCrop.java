package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmCropEntityListener;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(FarmCropEntityListener.class)
@Table(name = "farm_crops")
public class FarmCrop extends BaseEntityAudit {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "farm_animal_farm",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "farm_id")
    )
    @ToString.Exclude
    private List<Farm> farms;
}
