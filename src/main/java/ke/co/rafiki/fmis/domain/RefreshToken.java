package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.fmis.domain.entitylisteners.RefreshTokenEntityListener;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_tokens")
@EntityListeners(RefreshTokenEntityListener.class)
public class RefreshToken {

    @Id
    @Column(name = "token", nullable = false, updatable = false)
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @Column(name = "type", nullable = false, updatable = false)
    private String type;

    @Column(name = "expires_at", nullable = false, updatable = false)
    private Instant expiresAt;

}
