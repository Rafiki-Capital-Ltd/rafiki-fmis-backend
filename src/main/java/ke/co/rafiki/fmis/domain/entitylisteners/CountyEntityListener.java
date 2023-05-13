package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import ke.co.rafiki.fmis.domain.County;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountyEntityListener {
    @PrePersist
    private void prePersist(County county) {
        county.setName(county.getName().toUpperCase());
    }

    @PostPersist
    private void postPersist(County county) {
        log.info("Persisted county " + county);
    }

    @PostUpdate
    private void postUpdate(County county) { log.info("Updated county " + county); }

    @PostRemove
    private void postRemove(County county) {
        log.info("Removed county " + county);
    }
}
