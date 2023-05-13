package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.FarmActivity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FarmActivityEntityListener {
    @PostPersist
    private void postPersist(FarmActivity farmActivity) {
        log.info("Persisted farm activity " + farmActivity);
    }

    @PostUpdate
    private void postUpdate(FarmActivity farmActivity) { log.info("Updated farm activity " + farmActivity); }

    @PostRemove
    private void postRemove(FarmActivity farmActivity) {
        log.info("Removed farm activity " + farmActivity);
    }
}
