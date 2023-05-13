package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmActivityEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_activities")
@EntityListeners(FarmActivityEntityListener.class)
public class FarmActivity extends BaseEntityAudit {
    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_diary_id", nullable = false, updatable = false)
    private FarmActivityLog farmActivityLog;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "activities")
    private String activities;
}
