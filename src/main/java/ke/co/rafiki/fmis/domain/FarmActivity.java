package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_activities")
public class FarmActivity extends BaseEntityAudit {
    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_diary_id", nullable = false, updatable = false)
    private FarmActivityLog farmActivityLog;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "activities")
    private String activities;
}
