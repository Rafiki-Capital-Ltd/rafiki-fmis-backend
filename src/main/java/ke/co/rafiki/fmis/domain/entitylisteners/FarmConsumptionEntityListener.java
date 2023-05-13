package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.FarmConsumption;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FarmConsumptionEntityListener {
    @PostPersist
    private void postPersist(FarmConsumption farmConsumption) {
        log.info("Persisted farm consumption " + farmConsumption);
    }

    @PostUpdate
    private void postUpdate(FarmConsumption farmConsumption) { log.info("Updated farm consumption " + farmConsumption); }

    @PostRemove
    private void postRemove(FarmConsumption farmConsumption) {
        log.info("Removed farm consumption " + farmConsumption);
    }
}
