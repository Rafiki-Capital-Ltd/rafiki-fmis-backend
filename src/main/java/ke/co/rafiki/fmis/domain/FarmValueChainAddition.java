package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "value_chain_additions")
public class FarmValueChainAddition extends BaseEntityAudit {
    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_id", nullable = false)
    @ToString.Exclude
    private Farm farm;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "description", nullable = false)
    private String description;
}
