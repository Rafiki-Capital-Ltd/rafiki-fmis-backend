package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.Role;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoleEntityListener {
    @PostPersist
    private void postPersist(Role role) {
        log.info("Persisted role " + role);
    }

    @PostUpdate
    private void postUpdate(Role role) { log.info("Updated role " + role); }

    @PostRemove
    private void postRemove(Role role) {
        log.info("Removed role " + role);
    }
}
