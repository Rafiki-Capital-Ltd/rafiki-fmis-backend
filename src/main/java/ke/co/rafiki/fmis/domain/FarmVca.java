package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmVcaEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_value_chain_additions")
@EntityListeners(FarmVcaEntityListener.class)
public class FarmVca extends BaseEntityAudit {
    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_id", nullable = false, updatable = false)
    @ToString.Exclude
    private Farm farm;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
}
