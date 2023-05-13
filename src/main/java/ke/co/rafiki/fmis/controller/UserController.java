package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.dto.user.CreateUserDto;
import ke.co.rafiki.fmis.dto.user.GetUserDto;
import ke.co.rafiki.fmis.dto.user.UpdateUserDto;
import ke.co.rafiki.fmis.mapper.UserMapper;
import ke.co.rafiki.fmis.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<GetUserDto> createUser(
            HttpServletRequest request,
            @Valid @RequestBody CreateUserDto createUserDto
    ) throws Exception {
        User user = userService.save(userMapper.toUser(createUserDto));
        GetUserDto getUserDto = userMapper.toGetUserDto(user);
        URI location = new URI(request.getRequestURL() + "/" + user.getId());
        return ResponseEntity.created(location).body(getUserDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetUserDto>> findAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection
    ) {
        Page<User> users = userService.findAll(page, size, sort, sortDirection);
        List<GetUserDto> getUserDtos = users.stream()
                .map(userMapper::toGetUserDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getUserDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDto> findUserById(@PathVariable UUID id) throws Exception {
        User user = userService.findOne(id);
        GetUserDto getUserDto = userMapper.toGetUserDto(user);
        return ResponseEntity.ok(getUserDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUserDto> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserDto updateUserDto
    ) throws Exception {
        User user = userMapper.toUser(updateUserDto);
        GetUserDto getUserDto = userMapper.toGetUserDto(userService.update(id, user));
        return ResponseEntity.ok(getUserDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.delete(id);
    }
}
