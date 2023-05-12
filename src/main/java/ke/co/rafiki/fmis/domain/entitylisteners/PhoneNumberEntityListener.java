package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.PhoneNumber;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhoneNumberEntityListener {
    @PostPersist
    private void postPersist(PhoneNumber phoneNumber) {
        log.info("Persisted phone number " + phoneNumber);
    }

    @PostUpdate
    private void postUpdate(PhoneNumber phoneNumber) {
        log.info("Updated phone number " + phoneNumber);
    }

    @PostRemove
    private void postRemove(PhoneNumber phoneNumber) {
        log.info("Removed phone number " + phoneNumber);
    }
}
