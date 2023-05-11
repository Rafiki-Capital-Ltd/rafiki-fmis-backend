package ke.co.rafiki.rafikifmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PrePersist;
import ke.co.rafiki.rafikifmis.domain.Role;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoleEntityListener {

    @PrePersist
    private Role prePersist(Role role) {
        role.setName(role.getName().toUpperCase());
        return role;
    }

    @PostPersist
    private void postPersist(Role role) {
        log.info("Persisted role " + role);
    }

    @PostRemove
    private void postRemove(Role role) {
        log.info("Removed role " + role);
    }
}
