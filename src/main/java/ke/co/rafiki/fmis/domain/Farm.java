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
public class Farm extends BaseEntityAuditOwned {

    @Column(name = "name")
    private String name;

    @Column(name = "size", scale = 2)
    private BigDecimal size;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "county_id")
    private County county;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "constituency_id")
    private Constituency constituency;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "sub_county_id")
    private SubCounty subCounty;

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
