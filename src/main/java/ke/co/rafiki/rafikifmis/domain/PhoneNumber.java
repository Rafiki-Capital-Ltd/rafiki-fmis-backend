package ke.co.rafiki.rafikifmis.domain;

import jakarta.persistence.*;
import ke.co.rafiki.rafikifmis.domain.entitylisteners.PhoneNumberEntityListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "phone_numbers")
@EntityListeners({AuditingEntityListener.class, PhoneNumberEntityListener.class})
public class PhoneNumber {
    @Id @GeneratedValue
    private UUID id;

    @Column(name = "number", nullable = false, unique = true)
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
