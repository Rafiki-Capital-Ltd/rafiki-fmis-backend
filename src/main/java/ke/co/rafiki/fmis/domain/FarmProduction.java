package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmProductionEntityListener;
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
@Table(name = "farm_production_records")
@EntityListeners(FarmProductionEntityListener.class)
public class FarmProduction extends BaseEntityAuditOwned {
    // TODO: expenditures

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "quantity", scale = 2, nullable = false)
    private BigDecimal quantity;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

}
