package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.RefreshToken;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.dto.auth.LoginDto;
import ke.co.rafiki.fmis.dto.user.CreateUserDto;
import ke.co.rafiki.fmis.dto.user.GetUserDto;
import ke.co.rafiki.fmis.exceptions.BadRequestException;
import ke.co.rafiki.fmis.mapper.UserMapper;
import ke.co.rafiki.fmis.service.AuthService;
import ke.co.rafiki.fmis.service.RefreshTokenService;
import ke.co.rafiki.fmis.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static ke.co.rafiki.fmis.misc.HelperMethods.*;
import static ke.co.rafiki.fmis.misc.Constants.*;

@SuppressWarnings("unused")
@RestController
@RequestMapping("auth")
public class AuthController {

    @Value("${app.security.jwt.refresh-token.expires}")
    private Long refreshTokenExpiresMs;

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            AuthService authService, UserService userService, RefreshTokenService refreshTokenService,
            UserMapper userMapper, AuthenticationManager authenticationManager
    ) {
        this.authService = authService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<GetUserDto> register(
            HttpServletRequest request,
            @Valid @RequestBody CreateUserDto createUserDto
    ) throws Exception {
        User user = authService.registerUser(userMapper.toUser(createUserDto));
        GetUserDto getUserDto = userMapper.toGetUserDto(user);
        URI location = new URI(request.getRequestURL() + "/" + user.getId());
        return ResponseEntity.created(location).body(getUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @Valid @RequestBody LoginDto loginDto,
            HttpServletResponse response
    ) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        User user = userService.findOne(authentication.getName());
        GetUserDto getUserDto = userMapper.toGetUserDto(user);
        String accessToken = authService.generateAccessToken(authentication).getTokenValue();
        RefreshToken refreshToken = refreshTokenService.createToken(user);
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("user", getUserDto);
        responseBody.put("accessToken", accessToken);
        responseBody.put("refreshToken", refreshToken.getToken());
        responseBody.put("tokenType", refreshToken.getType());
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_KEY, refreshToken.getToken());
        cookie.setPath("/");
        cookie.setMaxAge(refreshTokenExpiresMs.intValue());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/profile")
    public ResponseEntity<GetUserDto> profile() throws Exception {
        User user = authService.profile();
        GetUserDto getUserDto = userMapper.toGetUserDto(user);
        return ResponseEntity.ok(getUserDto);
    }

    @GetMapping("/is-authenticated")
    public ResponseEntity<Boolean> isAuthenticated(Authentication authentication) {
        return ResponseEntity.ok(authentication.isAuthenticated());
    }

    @GetMapping("/access-token")
    public ResponseEntity<Map<String, Object>> getJwtToken() {
        Jwt accessToken = authService.generateAccessToken();
        HashMap<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken.getTokenValue());
        response.put("expiresAt", accessToken.getExpiresAt());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshToken(
            @CookieValue(name = REFRESH_TOKEN_COOKIE_KEY) String token,
            @RequestBody Map<String, String> requestBody
    ) throws Exception {
        String _token;
        if (token == null && requestBody.get("refreshToken") == null)
            throw new BadRequestException("Invalid refresh token");
        if (requestBody.get("refreshToken") != null)
            _token = requestBody.get("refreshToken");
        else
            _token = token;
        RefreshToken refreshToken = refreshTokenService.verifyToken(
                refreshTokenService.findByToken(_token)
        );
        Jwt accessToken = authService.generateAccessToken(refreshToken.getUser());
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken.getTokenValue());
        response.put("refreshToken", refreshToken.getToken());
        response.put("tokenType", refreshToken.getType());
        response.put("expiresAt", accessToken.getExpiresAt());
        return ResponseEntity.ok(response);
    }
}
