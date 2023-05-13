package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.FarmActivityLog;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FarmActivityLogEntityListener {
    @PostPersist
    private void postPersist(FarmActivityLog farmActivityLog) {
        log.info("Persisted farm activity log " + farmActivityLog);
    }

    @PostUpdate
    private void postUpdate(FarmActivityLog farmActivityLog) { log.info("Updated farm activity log " + farmActivityLog); }

    @PostRemove
    private void postRemove(FarmActivityLog farmActivityLog) {
        log.info("Removed farm activity log " + farmActivityLog);
    }
}
