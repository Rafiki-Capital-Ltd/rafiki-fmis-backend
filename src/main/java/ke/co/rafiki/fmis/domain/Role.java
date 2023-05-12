package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.RoleEntityListener;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@EntityListeners({RoleEntityListener.class})
public class Role extends BaseEntityAudit {
    @Column(name = "type", unique = true, nullable = false)
    private RoleType type;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private List<User> users;
}
