package ke.co.rafiki.fmis.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@MappedSuperclass // ensures that the base entity class will not have a separate representation as table of the extending class
public abstract class BaseEntity implements Serializable { // use abstract class to prevent instantiation
    @Id @GeneratedValue
    @Column(
            columnDefinition = "BINARY(16)", // makes the value of UUID human readable
            updatable = false,
            nullable = false
    )
    private UUID id;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
