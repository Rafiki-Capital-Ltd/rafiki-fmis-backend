package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Role;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.service.AuthService;
import ke.co.rafiki.fmis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

import static ke.co.rafiki.fmis.misc.HelperMethods.*;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Value("${app.security.jwt.access-token.expires}")
    private Long accessTokenExpires;

    private final UserService userService;
    private final JwtEncoder jwtEncoder;

    public AuthServiceImpl(UserService userService, JwtEncoder jwtEncoder) {
        this.userService = userService;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public User registerUser(User user) throws Exception {
        return userService.save(user);
    }

    @Override
    public User profile() throws Exception {
        Authentication authentication = getAuthentication();
        return userService.findOne(authentication.getName());
    }

    @Override
    public Jwt generateAccessToken() {
        Authentication authentication = getAuthentication();
        return getAccessToken(authentication);
    }

    @Override
    public Jwt generateAccessToken(Authentication authentication) {
        return getAccessToken(authentication);
    }

    @Override
    public Jwt generateAccessToken(User user) { return  getAccessToken(user); }

    private Jwt getAccessToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusMillis(accessTokenExpires))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        JwtEncoderParameters encoderParameters = JwtEncoderParameters
                .from(JwsHeader.with(MacAlgorithm.HS512).build(), claims);
        return this.jwtEncoder.encode(encoderParameters);
    }

    private Jwt getAccessToken(User user) {
        Instant now = Instant.now();
        String scope = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusMillis(accessTokenExpires))
                .subject(user.getEmail())
                .claim("scope", scope)
                .build();
        JwtEncoderParameters encoderParameters = JwtEncoderParameters
                .from(JwsHeader.with(MacAlgorithm.HS512).build(), claims);
        return this.jwtEncoder.encode(encoderParameters);
    }
}
