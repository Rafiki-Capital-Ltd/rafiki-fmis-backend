package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmProductionEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_production_records")
@EntityListeners(FarmProductionEntityListener.class)
public class FarmProduction extends BaseEntityAudit {
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