package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.FarmPurchase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FarmPurchaseEntityListener {
    @PostPersist
    private void postPersist(FarmPurchase farmPurchase) {
        log.info("Persisted farm purchase " + farmPurchase);
    }

    @PostUpdate
    private void postUpdate(FarmPurchase farmPurchase) { log.info("Updated farm purchase " + farmPurchase); }

    @PostRemove
    private void postRemove(FarmPurchase farmPurchase) {
        log.info("Removed farm purchase " + farmPurchase);
    }
}