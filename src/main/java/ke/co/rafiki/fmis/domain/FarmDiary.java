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
@Table(name = "farm_diaries")
public class FarmDiary extends BaseEntityAudit {
    @OneToOne(mappedBy = "farmDiary")
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @OneToMany(mappedBy = "farmDiary", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FarmActivity> farmActivities;
}
