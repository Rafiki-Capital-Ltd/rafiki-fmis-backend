package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.CountyEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "counties")
@EntityListeners(CountyEntityListener.class)
public class County extends BaseEntityAudit {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "county", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Ward> wards;

    @OneToMany(mappedBy = "county", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Farm> farms;
}
