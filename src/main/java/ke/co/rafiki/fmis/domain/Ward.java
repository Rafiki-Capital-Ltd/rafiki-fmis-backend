package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.WardEntityListener;
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
@Table(name = "wards")
@EntityListeners(WardEntityListener.class)
public class Ward extends BaseEntityAudit{
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "county_id", nullable = false)
    private County county;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Farm> farms;
}
