package ke.co.rafiki.fmis;

import ke.co.rafiki.fmis.domain.*;
import ke.co.rafiki.fmis.domain.enums.GenderType;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AppInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountyRepository countyRepository;

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Override
    public void run(String... args) throws Exception {
        initRoles();
        List<Gender> genders = initGenders();
        List<User> users = initUsers(genders);
        List<County> counties = initCounties();
        List<Ward> wards = initWards(counties);
        List<Farm> farms = initFarms(users, counties, wards);
    }

    private void initRoles() {
        roleRepository.deleteAll();
        roleRepository.saveAll(
                Arrays.stream(RoleType.values())
                    .map(type -> Role.builder().name(type.toString()).build())
                    .collect(Collectors.toSet())
        );
    }

    private List<Gender> initGenders() {
        genderRepository.deleteAll();
        return genderRepository.saveAll(
                Arrays.stream(GenderType.values())
                        .map(gender -> Gender.builder().name(gender.toString()).build())
                        .collect(Collectors.toSet())
        );
    }

    private List<User> initUsers(List<Gender> genders) throws Exception {
        Role farmerRole = roleRepository.findByName(RoleType.FARMER.toString())
                .orElseThrow(NotFoundException::new);
        Role managerRole = roleRepository.findByName(RoleType.MANAGER.toString())
                .orElseThrow(NotFoundException::new);
        Role adminRole = roleRepository.findByName(RoleType.ADMIN.toString())
                .orElseThrow(NotFoundException::new);
        List<User> users = List.of(
                User.builder()
                        .firstName("Farmer")
                        .lastName("User")
                        .email("farmer@fmis.rafiki.co.ke")
                        .password("password")
                        .gender(genders.get(0))
                        .roles(Set.of(farmerRole))
                        .build(),
                User.builder()
                        .firstName("Manager")
                        .lastName("User")
                        .email("manager@fmis.rafiki.co.ke")
                        .password("password")
                        .gender(genders.get(0))
                        .roles(Set.of(farmerRole, managerRole))
                        .build(),
                User.builder()
                        .firstName("Admin")
                        .lastName("User")
                        .email("admin@fmis.rafiki.co.ke")
                        .password("password")
                        .gender(genders.get(1))
                        .roles(Set.of(farmerRole, managerRole, adminRole))
                        .build()
        );
        return userRepository.saveAll(users);
    }

    private List<County> initCounties() {
        countyRepository.deleteAll();
        List<County> counties = List.of(
                County.builder().name("NAIROBI").build(),
                County.builder().name("MAKUENI").build(),
                County.builder().name("MACHAKOS").build(),
                County.builder().name("KITUI").build(),
                County.builder().name("MOMBASA").build(),
                County.builder().name("NYERI").build()
        );
        return countyRepository.saveAll(counties);
    }

    private List<Ward> initWards(List<County> counties) {
        List<Ward> wards = List.of(
                Ward.builder().name("WARD 1").county(counties.get(1)).build(),
                Ward.builder().name("WARD 2").county(counties.get(1)).build(),
                Ward.builder().name("WARD 3").county(counties.get(2)).build(),
                Ward.builder().name("WARD 4").county(counties.get(2)).build(),
                Ward.builder().name("WARD 5").county(counties.get(3)).build(),
                Ward.builder().name("WARD 6").county(counties.get(3)).build()
        );
        return wardRepository.saveAll(wards);
    }

    private List<Farm> initFarms(List<User> users, List<County> counties, List<Ward> wards) {
        farmRepository.deleteAll();
        List<Farm> farms = List.of(
                Farm.builder()
                        .name("Test Farm 1")
                        .owner(users.get(0))
                        .size(BigDecimal.valueOf(12.0))
                        .county(counties.get(1))
                        .ward(wards.get(0))
                        .build(),
                Farm.builder()
                        .name("Test Farm 2")
                        .owner(users.get(1))
                        .size(BigDecimal.valueOf(10.0))
                        .county(counties.get(0))
                        .ward(wards.get(0))
                        .build()
        );
        return farmRepository.saveAll(farms);
    }
}
