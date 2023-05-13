package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import ke.co.rafiki.fmis.domain.Ward;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WardEntityListener {
    @PrePersist
    private Ward prePersist(Ward ward) {
        ward.setName(ward.getName().toUpperCase());
        return ward;
    }

    @PostPersist
    private void postPersist(Ward ward) {
        log.info("Persisted ward " + ward);
    }

    @PostUpdate
    private void postUpdate(Ward ward) { log.info("Updated ward " + ward); }

    @PostRemove
    private void postRemove(Ward ward) {
        log.info("Removed ward " + ward);
    }
}
