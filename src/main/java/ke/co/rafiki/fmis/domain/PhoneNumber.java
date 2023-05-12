package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.PhoneNumberEntityListener;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@ToString
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
