package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.RefreshToken;
import ke.co.rafiki.fmis.domain.User;

public interface RefreshTokenService {

    RefreshToken findByToken(String token) throws Exception;

    RefreshToken createToken(User user) throws Exception;

    RefreshToken verifyToken(RefreshToken refreshToken) throws Exception;

    void deleteByUser(User user) throws Exception;
}
