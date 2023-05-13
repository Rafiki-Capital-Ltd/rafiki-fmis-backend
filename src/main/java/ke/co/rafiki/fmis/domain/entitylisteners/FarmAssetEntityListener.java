package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.FarmAsset;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FarmAssetEntityListener {
    @PostPersist
    private void postPersist(FarmAsset farmAsset) {
        log.info("Persisted farm asset " + farmAsset);
    }

    @PostUpdate
    private void postUpdate(FarmAsset farmAsset) { log.info("Updated farm asset " + farmAsset); }

    @PostRemove
    private void postRemove(FarmAsset farmAsset) {
        log.info("Removed farm asset " + farmAsset);
    }
}
