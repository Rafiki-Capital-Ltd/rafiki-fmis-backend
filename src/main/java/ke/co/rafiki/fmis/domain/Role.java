package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.RoleEntityListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
