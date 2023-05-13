package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.GenderEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "genders")
@EntityListeners(GenderEntityListener.class)
public class Gender extends BaseEntityAudit {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "gender", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<User> users;
}
