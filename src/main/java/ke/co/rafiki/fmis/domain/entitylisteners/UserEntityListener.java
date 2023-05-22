package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserEntityListener {

    @PostPersist
    private void postPersist(User user) {
        log.info("Persisted user " + user);
    }

    @PostUpdate
    private void postUpdate(User user) {
        log.info("updated user " + user);
    }

    @PostRemove
    private void postRemove(User user) {
        log.info("Removed user " + user);
    }

}
