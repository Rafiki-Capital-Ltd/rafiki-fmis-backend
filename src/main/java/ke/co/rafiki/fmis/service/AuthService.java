package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.exceptions.BadRequestException;
import ke.co.rafiki.fmis.exceptions.NotFoundException;

import java.security.Principal;

public interface AuthService {
    User registerUser(User user) throws Exception;

    User profile(Principal principal) throws Exception;
}
