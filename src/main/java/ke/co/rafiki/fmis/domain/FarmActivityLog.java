package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_activity_logs")
public class FarmActivityLog extends BaseEntityAudit {
    @OneToOne(mappedBy = "farmActivityLog")
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @OneToMany(mappedBy = "farmActivityLog", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FarmActivity> farmActivities;
}
