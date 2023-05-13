package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.GenderEntityListener;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "genders")
@EntityListeners(GenderEntityListener.class)
public class Gender extends BaseEntityAudit {
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;
}
