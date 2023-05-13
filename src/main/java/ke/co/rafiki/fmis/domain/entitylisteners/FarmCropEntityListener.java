package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.FarmCrop;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FarmCropEntityListener {
    @PostPersist
    private void postPersist(FarmCrop farmCrop) {
        log.info("Persisted farm crop " + farmCrop);
    }

    @PostUpdate
    private void postUpdate(FarmCrop farmCrop) { log.info("Updated farm crop " + farmCrop); }

    @PostRemove
    private void postRemove(FarmCrop farmCrop) {
        log.info("Removed farm crop " + farmCrop);
    }
}
