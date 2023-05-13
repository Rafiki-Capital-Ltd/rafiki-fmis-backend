package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmConsumptionEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_consumptions")
@EntityListeners(FarmConsumptionEntityListener.class)
public class FarmConsumption extends BaseEntityAudit {
    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_id", nullable = false, updatable = false)
    private Farm farm;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "quantity", scale = 2, nullable = false)
    private BigDecimal quantity;

    @Column(name = "description")
    private String description;
}
