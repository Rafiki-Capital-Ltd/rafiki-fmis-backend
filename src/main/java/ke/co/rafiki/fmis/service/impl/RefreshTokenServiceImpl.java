package ke.co.rafiki.fmis.service.impl;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.RefreshToken;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.exceptions.RefreshTokenException;
import ke.co.rafiki.fmis.repository.RefreshTokenRepository;
import ke.co.rafiki.fmis.service.RefreshTokenService;
import ke.co.rafiki.fmis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Value("${app.security.jwt.refresh-token.expires}")
    private Long refreshTokenExpiresMs;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserService userService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
    }

    @Override
    public RefreshToken findByToken(String token) throws Exception {
        return refreshTokenRepository.findByToken(token).orElseThrow(() -> {
            String message = String.format("Refresh token %s was not found.", token);
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    @Transactional
    public RefreshToken createToken(User user) throws Exception {
        return refreshTokenRepository.findByUser(user)
                .orElseGet(() -> refreshTokenRepository.save(
                    RefreshToken.builder()
                            .token(UUID.randomUUID().toString())
                            .user(user)
                            .type("Bearer")
                            .expiresAt(Instant.now().plusMillis(refreshTokenExpiresMs))
                            .build()
                        )
                );
    }

    @Override
    public RefreshToken verifyToken(RefreshToken refreshToken) throws Exception {
        if (refreshToken.getExpiresAt().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenException(refreshToken.getToken(), "Refresh token expired.");
        }
        return refreshToken;
    }

    @Override
    public void deleteByUser(User user) throws Exception {
        User _user = userService.findOne(user.getId());
        refreshTokenRepository.deleteByUser(_user);
    }
}
