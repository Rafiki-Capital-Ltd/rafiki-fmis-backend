package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmActivityLogEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Year;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_activity_logs")
@EntityListeners(FarmActivityLogEntityListener.class)
public class FarmActivityLog extends BaseEntityAuditOwned {
    @ManyToOne
    @JoinColumn(name = "farm_id")
    @ToString.Exclude
    private Farm farm;

    @Column(name = "year", nullable = false, updatable = false)
    private Year year;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "farmActivityLog", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<FarmActivity> farmActivities;
}
