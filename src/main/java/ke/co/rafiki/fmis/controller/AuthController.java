package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.dto.auth.LoginDto;
import ke.co.rafiki.fmis.dto.user.CreateUserDto;
import ke.co.rafiki.fmis.dto.user.GetUserDto;
import ke.co.rafiki.fmis.mapper.UserMapper;
import ke.co.rafiki.fmis.service.AuthService;
import ke.co.rafiki.fmis.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static ke.co.rafiki.fmis.misc.HelperMethods.*;

@SuppressWarnings("unused")
@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            AuthService authService, UserService userService,
            UserMapper userMapper, AuthenticationManager authenticationManager
    ) {
        this.authService = authService;
        this.userService = userService;
        this.userMapper = userMapper;
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
            @Valid @RequestBody LoginDto loginDto
    ) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        User user = userService.findOne(authentication.getName());
        GetUserDto getUserDto = userMapper.toGetUserDto(user);
        String accessToken = authService.generateAccessToken(authentication).getTokenValue();
        HashMap<String, Object> response = new HashMap<>();
        response.put("user", getUserDto);
        response.put("accessToken", accessToken);
        return ResponseEntity.ok(response);
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
}
