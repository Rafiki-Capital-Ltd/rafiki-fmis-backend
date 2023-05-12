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

    @Column(name = "name")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner", nullable = false, updatable = false)
    private User owner;

    @Column(name = "size", scale = 2)
    private BigDecimal size;

    @Column(name = "county", nullable = false)
    private String county;

    @Column(name = "ward")
    private String ward;

    @Column(name = "nearest_shopping_center")
    private String nearestShoppingCenter;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private FarmLocation location;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private FarmActivityLog farmActivityLog;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FarmValueChainAddition> farmValueChainAdditions;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FarmAsset> farmAssets;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FarmProduction> farmProductions;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FarmConsumption> farmConsumptions;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FarmSale> farmSales;
}
