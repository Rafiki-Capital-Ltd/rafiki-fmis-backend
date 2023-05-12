package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_asset_register")
public class FarmAssetRegister extends BaseEntityAudit {
    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    private AssetStatus status = AssetStatus.FUNCTIONAL;
}
