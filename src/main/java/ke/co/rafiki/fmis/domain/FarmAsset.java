package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmAssetEntityListener;
import ke.co.rafiki.fmis.domain.enums.AssetStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_assets")
@EntityListeners(FarmAssetEntityListener.class)
public class FarmAsset extends BaseEntityAuditOwned {

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "storage_location")
    private String storageLocation;

    @Column(name = "status", nullable = false)
    private String status = AssetStatus.FUNCTIONAL.toString();

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

}
