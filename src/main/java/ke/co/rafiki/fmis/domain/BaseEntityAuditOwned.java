package ke.co.rafiki.fmis.domain;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntityAuditOwned extends BaseEntityAudit implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
}
