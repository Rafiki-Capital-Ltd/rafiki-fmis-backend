package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Role;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.service.AuthService;
import ke.co.rafiki.fmis.service.RoleService;
import ke.co.rafiki.fmis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Set;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User registerUser(User user) throws Exception {
        return userService.save(user);
    }

    @Override
    public User profile(Principal principal) throws Exception {
        return userService.findOne(principal.getName());
    }
}
