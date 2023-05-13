package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmLocationEntityListener;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_locations")
@EntityListeners(FarmLocationEntityListener.class)
public class FarmLocation extends BaseEntityAudit {
    @OneToOne(mappedBy = "location")
    @JoinColumn(name = "farm_id", nullable = false, updatable = false)
    private Farm farm;

    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;
}
