package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmAssetEntityListener;
import ke.co.rafiki.fmis.domain.enums.AssetStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Builder
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_assets")
@EntityListeners(FarmAssetEntityListener.class)
public class FarmAsset extends BaseEntityAudit {
    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_id", nullable = false, updatable = false)
    private Farm farm;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "storage_location")
    private String storageLocation;

    @Column(name = "status", nullable = false)
    private AssetStatus status = AssetStatus.FUNCTIONAL;
}
