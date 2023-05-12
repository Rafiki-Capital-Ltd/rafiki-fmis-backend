package ke.co.rafiki.fmis;

import ke.co.rafiki.fmis.domain.Role;
import ke.co.rafiki.fmis.domain.RoleType;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.RoleRepository;
import ke.co.rafiki.fmis.repository.UserRepository;
import ke.co.rafiki.fmis.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SeedDbData implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        createRoles();
        createUsers();
    }

    public void createRoles() {
        roleRepository.deleteAll();
        roleRepository.saveAll(
                Arrays.stream(RoleType.values())
                    .map(type -> Role.builder().type(type).build())
                    .collect(Collectors.toSet())
        );
    }

    public void createUsers() throws Exception {
        Role farmerRole = roleRepository.findByType(RoleType.FARMER)
                .orElseThrow(NotFoundException::new);
        Role managerRole = roleRepository.findByType(RoleType.MANAGER)
                .orElseThrow(NotFoundException::new);
        Role adminRole = roleRepository.findByType(RoleType.ADMIN)
                .orElseThrow(NotFoundException::new);
        List<User> users = List.of(
                User.builder()
                        .firstName("Farmer")
                        .lastName("User")
                        .email("farmer@fmis.rafiki.co.ke")
                        .password("password")
                        .roles(Set.of(farmerRole))
                        .build(),
                User.builder()
                        .firstName("Manager")
                        .lastName("User")
                        .email("farmer@fmis.rafiki.co.ke")
                        .password("password")
                        .roles(Set.of(farmerRole, managerRole))
                        .build(),
                User.builder()
                        .firstName("Admin")
                        .lastName("User")
                        .email("farmer@fmis.rafiki.co.ke")
                        .password("password")
                        .roles(Set.of(farmerRole, managerRole, adminRole))
                        .build()
        );
        userRepository.saveAll(users);
    }
}
