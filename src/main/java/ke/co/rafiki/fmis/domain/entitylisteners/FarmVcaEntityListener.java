package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.FarmVca;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FarmVcaEntityListener {
    @PostPersist
    private void postPersist(FarmVca farmVca) {
        log.info("Persisted farm vca " + farmVca);
    }

    @PostUpdate
    private void postUpdate(FarmVca farmVca) { log.info("Updated farm vca " + farmVca); }

    @PostRemove
    private void postRemove(FarmVca farmVca) {
        log.info("Removed farm vca " + farmVca);
    }
}
