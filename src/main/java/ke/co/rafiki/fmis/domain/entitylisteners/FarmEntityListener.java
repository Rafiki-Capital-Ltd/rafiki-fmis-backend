package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.Farm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FarmEntityListener {
    @PostPersist
    private void postPersist(Farm farm) {
        log.info("Persisted farm " + farm);
    }

    @PostUpdate
    private void postUpdate(Farm farm) { log.info("Updated farm " + farm); }

    @PostRemove
    private void postRemove(Farm farm) {
        log.info("Removed farm " + farm);
    }
}
