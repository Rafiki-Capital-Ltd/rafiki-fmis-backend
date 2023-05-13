package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.PhoneNumberEntityListener;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "phone_numbers")
@EntityListeners({PhoneNumberEntityListener.class})
public class PhoneNumber extends BaseEntityAudit {
    @Column(name = "number", nullable = false, unique = true)
    private String number;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;
}
