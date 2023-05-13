package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.Gender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenderEntityListener {
    @PostPersist
    private void postPersist(Gender gender) {
        log.info("Persisted gender " + gender);
    }

    @PostUpdate
    private void postUpdate(Gender gender) { log.info("Updated gender " + gender); }

    @PostRemove
    private void postRemove(Gender gender) {
        log.info("Removed gender " + gender);
    }
}
