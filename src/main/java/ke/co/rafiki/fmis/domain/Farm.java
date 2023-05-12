package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farms")
public class Farm extends BaseEntityAudit {
    @OneToMany(mappedBy = "farms")
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @Column(name = "size", scale = 2)
    private BigDecimal size;

    @Column(name = "county", nullable = false)
    private String county;

    @Column(name = "ward")
    private String ward;

    @Column(name = "nearest_shopping_center")
    private String nearestShoppingCenter;

    @OneToOne(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    private FarmLocation location;

    @OneToOne(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private FarmDiary farmDiary;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FarmValueChainAddition> farmValueChainAdditions;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FarmAssetRegister> farmAssetRegisters;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FarmProductionRecord> farmProductionRecords;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FarmConsumptionRecord> farmConsumptionRecords;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FarmSalesRecord> farmSalesRecords;
}
