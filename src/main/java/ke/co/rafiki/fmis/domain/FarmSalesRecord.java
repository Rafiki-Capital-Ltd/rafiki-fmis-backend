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
@Table(name = "farm_sales_records")
public class FarmSalesRecord extends BaseEntityAudit {

    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_id", nullable = false, updatable = false)
    @ToString.Exclude
    private Farm farm;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "type", nullable = false)
    private SalesType type;

    @Column(name = "amount", scale = 2)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

}
