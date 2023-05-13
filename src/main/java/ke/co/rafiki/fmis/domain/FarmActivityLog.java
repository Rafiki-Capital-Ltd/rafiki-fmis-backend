package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmActivityLogEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Year;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_activity_logs")
@EntityListeners(FarmActivityLogEntityListener.class)
public class FarmActivityLog extends BaseEntityAudit {
    @OneToOne(mappedBy = "farmActivityLog")
    @JoinColumn(name = "farm_id", nullable = false)
    @ToString.Exclude
    private Farm farm;

    @Column(name = "year", nullable = false, updatable = false)
    private Year year;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "farmActivityLog", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FarmActivity> farmActivities;
}
