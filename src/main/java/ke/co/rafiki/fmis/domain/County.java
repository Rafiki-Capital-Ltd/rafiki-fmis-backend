package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "counties")
public class County extends BaseEntityAudit {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Ward> wards;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Farm> farms;
}
