package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmActivityEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_activities")
@EntityListeners(FarmActivityEntityListener.class)
public class FarmActivity extends BaseEntityAuditOwned {
    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_diary_id", nullable = false, updatable = false)
    @ToString.Exclude
    private FarmActivityLog farmActivityLog;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "activities")
    private String activities;
}
