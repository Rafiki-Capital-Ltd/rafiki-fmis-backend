package ke.co.rafiki.fmis.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmAssetEntityListener;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmExpenseEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_expenses")
@EntityListeners(FarmExpenseEntityListener.class)
public class FarmExpense extends BaseEntityAuditOwned {
    @Column(name = "type")
    private String type;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "description")
    private String description;
}
