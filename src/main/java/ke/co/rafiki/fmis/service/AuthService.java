package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.exceptions.BadRequestException;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;

import java.security.Principal;

public interface AuthService {
    User registerUser(User user) throws Exception;
    User profile() throws Exception;
    Jwt generateAccessToken();
}
