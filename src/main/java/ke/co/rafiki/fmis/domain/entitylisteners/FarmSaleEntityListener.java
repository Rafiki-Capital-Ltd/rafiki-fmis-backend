package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.FarmSale;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FarmSaleEntityListener {
    @PostPersist
    private void postPersist(FarmSale farmSale) {
        log.info("Persisted farm sale " + farmSale);
    }

    @PostUpdate
    private void postUpdate(FarmSale farmSale) { log.info("Updated farm sale " + farmSale); }

    @PostRemove
    private void postRemove(FarmSale farmSale) {
        log.info("Removed farm sale " + farmSale);
    }
}
