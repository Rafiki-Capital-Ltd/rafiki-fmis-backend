package ke.co.rafiki.fmis.domain.entitylisteners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import ke.co.rafiki.fmis.domain.RefreshToken;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RefreshTokenEntityListener {

    @PostPersist
    private void postPersist(RefreshToken refreshToken) {
        log.info("Persisted refresh token " + refreshToken);
    }

    @PostUpdate
    private void postUpdate(RefreshToken refreshToken) { log.info("Updated refresh token " + refreshToken); }

    @PostRemove
    private void postRemove(RefreshToken refreshToken) {
        log.info("Removed refresh token " + refreshToken);
    }

}
