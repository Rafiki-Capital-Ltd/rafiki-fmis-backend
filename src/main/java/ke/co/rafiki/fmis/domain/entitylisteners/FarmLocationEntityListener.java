package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.FarmLocation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FarmLocationEntityListener {
    @PostPersist
    private void postPersist(FarmLocation farmLocation) {
        log.info("Persisted farm location " + farmLocation);
    }

    @PostUpdate
    private void postUpdate(FarmLocation farmLocation) { log.info("Updated farm location " + farmLocation); }

    @PostRemove
    private void postRemove(FarmLocation farmLocation) {
        log.info("Removed farm location " + farmLocation);
    }
}
