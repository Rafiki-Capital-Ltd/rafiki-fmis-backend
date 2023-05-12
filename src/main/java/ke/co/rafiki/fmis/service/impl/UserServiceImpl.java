package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.exceptions.BadRequestException;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.UserRepository;
import ke.co.rafiki.fmis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<User> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return userRepository.findAll(pageRequest);
    }

    @Override
    public User findOne(UUID id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> {
            String message = String.format("User with id %s was not found.", id);
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public User findOne(String email) throws NotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            String message = String.format("User with email %s was not found.",email);
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public User save(User user) throws BadRequestException {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            String message = String.format("User with email " + user.getEmail() + " already exists.");
            log.error(message);
            throw new BadRequestException(message);
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public User update(UUID id, User user) throws NotFoundException {
        User existingUser = this.findOne(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setMiddleName(user.getMiddleName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPhoneNumbers(user.getPhoneNumbers());
        existingUser.setRoles(user.getRoles());
        return userRepository.save(existingUser);
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
