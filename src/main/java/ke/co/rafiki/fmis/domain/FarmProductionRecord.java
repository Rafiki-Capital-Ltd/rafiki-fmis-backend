package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_production_records")
public class FarmProductionRecord extends BaseEntityAudit {
    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "quantity", scale = 2)
    private BigDecimal quantity;

}
