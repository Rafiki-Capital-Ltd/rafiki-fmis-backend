package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.FarmEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farms")
@EntityListeners(FarmEntityListener.class)
public class Farm extends BaseEntityAudit {

    @Column(name = "name")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @ToString.Exclude
    private User owner;

    @Column(name = "size", scale = 2)
    private BigDecimal size;

    @ManyToOne(optional = false)
    @JoinColumn(name = "county_id", nullable = false)
    @ToString.Exclude
    private County county;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ward_id", nullable = false)
    @ToString.Exclude
    private Ward ward;

    @Column(name = "nearest_shopping_center")
    private String nearestShoppingCenter;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private FarmLocation location;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<FarmCrop> farmCrops;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<FarmAnimal> farmAnimals;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<FarmActivityLog> farmActivityLogs;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<FarmVca> farmVcas;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<FarmAsset> farmAssets;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<FarmProduction> farmProductions;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<FarmConsumption> farmConsumptions;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<FarmSale> farmSales;
}
