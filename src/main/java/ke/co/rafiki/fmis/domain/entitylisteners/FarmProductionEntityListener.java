package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.FarmProduction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FarmProductionEntityListener {
    @PostPersist
    private void postPersist(FarmProduction farmProduction) {
        log.info("Persisted farm production " + farmProduction);
    }

    @PostUpdate
    private void postUpdate(FarmProduction farmProduction) { log.info("Updated farm production " + farmProduction); }

    @PostRemove
    private void postRemove(FarmProduction farmProduction) {
        log.info("Removed farm production " + farmProduction);
    }
}
