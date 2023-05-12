package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PrePersist;
import ke.co.rafiki.fmis.domain.Role;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoleEntityListener {
    @PostPersist
    private void postPersist(Role role) {
        log.info("Persisted role " + role);
    }

    @PostRemove
    private void postRemove(Role role) {
        log.info("Removed role " + role);
    }
}
