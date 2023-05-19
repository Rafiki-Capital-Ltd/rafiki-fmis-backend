package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.dto.user.CreateUserDto;
import ke.co.rafiki.fmis.dto.user.GetUserDto;
import ke.co.rafiki.fmis.mapper.UserMapper;
import ke.co.rafiki.fmis.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@SuppressWarnings("unused")
@RestController
@RequestMapping("auth")
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<GetUserDto> register(
            HttpServletRequest request,
            @Valid @RequestBody CreateUserDto createUserDto
    ) throws Exception {
        User user = userService.save(userMapper.toUser(createUserDto));
        GetUserDto getUserDto = userMapper.toGetUserDto(user);
        URI location = new URI(request.getRequestURL() + "/" + user.getId());
        return ResponseEntity.created(location).body(getUserDto);
    }

    @GetMapping("/login")
    public ResponseEntity<GetUserDto> login(Principal principal) throws Exception {
        User user = userService.findOne(principal.getName());
        GetUserDto getUserDto = userMapper.toGetUserDto(user);
        return ResponseEntity.ok(getUserDto);
    }

    @GetMapping("/profile")
    public ResponseEntity<GetUserDto> profile(Principal principal) throws Exception {
        User user = userService.findOne(principal.getName());
        GetUserDto getUserDto = userMapper.toGetUserDto(user);
        return ResponseEntity.ok(getUserDto);
    }
}
