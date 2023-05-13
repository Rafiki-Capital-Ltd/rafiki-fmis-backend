package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.FarmAnimal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FarmAnimalEntityListener {
    @PostPersist
    private void postPersist(FarmAnimal farmAnimal) {
        log.info("Persisted farm animal " + farmAnimal);
    }

    @PostUpdate
    private void postUpdate(FarmAnimal farmAnimal) { log.info("Updated farm animal " + farmAnimal); }

    @PostRemove
    private void postRemove(FarmAnimal farmAnimal) {
        log.info("Removed farm animal " + farmAnimal);
    }
}
