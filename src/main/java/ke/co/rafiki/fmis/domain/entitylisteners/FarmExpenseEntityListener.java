package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.FarmExpense;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FarmExpenseEntityListener {
    @PostPersist
    private void postPersist(FarmExpense farmExpense) {
        log.info("Persisted farm expense " + farmExpense);
    }

    @PostUpdate
    private void postUpdate(FarmExpense farmExpense) { log.info("Updated farm expense " + farmExpense); }

    @PostRemove
    private void postRemove(FarmExpense farmExpense) {
        log.info("Removed farm expense " + farmExpense);
    }
}
