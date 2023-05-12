package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.UserEntityListener;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EntityListeners(UserEntityListener.class)
public class User extends BaseEntityAudit {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<PhoneNumber> phoneNumbers;

    @Column(name = "display_picture")
    private String displayPicture;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private Set<Role> roles;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Farm> farms;
}
