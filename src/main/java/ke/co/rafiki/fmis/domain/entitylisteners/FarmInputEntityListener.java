package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.FarmInput;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FarmInputEntityListener {
    @PostPersist
    private void postPersist(FarmInput farmInput) {
        log.info("Persisted farm input " + farmInput);
    }

    @PostUpdate
    private void postUpdate(FarmInput farmInput) { log.info("Updated farm input " + farmInput); }

    @PostRemove
    private void postRemove(FarmInput farmInput) {
        log.info("Removed farm input " + farmInput);
    }
}
