package ke.co.rafiki.fmis;

import ke.co.rafiki.fmis.domain.*;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Component
@Profile("production")
public class AppInitializer implements CommandLineRunner {

    @Value("${app.credentials.admin.first-name}")
    private String adminFirstName;

    @Value("${app.credentials.admin.last-name}")
    private String adminLastName;

    @Value("${app.credentials.admin.email}")
    private String adminEmail;

    @Value("${app.credentials.admin.password}")
    private String adminPassword;

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public AppInitializer(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        initRoles();
        createAdminUser();
    }

    public void initRoles() {
        roleRepository.findAll()
                .forEach(role -> roleRepository.disassociateRoleFromUser(role.getId()));
        Arrays.stream(RoleType.values())
                        .forEach(role -> {
                            Optional<Role> _role = roleRepository.findByName(role.toString());
                            if (_role.isEmpty()) {
                                roleRepository.save(
                                        Role.builder()
                                                .name(role.toString())
                                                .build()
                                );
                            }
                        });
    }

    public void createAdminUser() {
        Role farmerRole = roleRepository.findByName(RoleType.FARMER.toString())
                .orElseThrow(NotFoundException::new);
        Role managerRole = roleRepository.findByName(RoleType.MANAGER.toString())
                .orElseThrow(NotFoundException::new);
        Role adminRole = roleRepository.findByName(RoleType.ADMIN.toString())
                .orElseThrow(NotFoundException::new);
        User adminUser = User.builder()
                    .firstName(adminFirstName)
                    .lastName(adminLastName)
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .roles(Set.of(farmerRole, managerRole, adminRole))
                    .build();
        Optional<User> _adminUser = userRepository.findByEmail(adminUser.getEmail());
        if (_adminUser.isEmpty()) {
            roleRepository.saveAll(List.of(farmerRole, managerRole, adminRole));
            userRepository.save(adminUser);
        }
    }
}
