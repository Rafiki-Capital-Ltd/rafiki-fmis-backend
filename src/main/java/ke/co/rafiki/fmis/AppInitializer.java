package ke.co.rafiki.fmis;

import ke.co.rafiki.fmis.domain.*;
import ke.co.rafiki.fmis.domain.enums.AssetStatus;
import ke.co.rafiki.fmis.domain.enums.GenderType;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.domain.enums.SaleType;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Component
public class AppInitializer implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Autowired
    private FarmAnimalRepository farmAnimalRepository;

    @Autowired
    private FarmCropRepository farmCropRepository;

    @Autowired
    private FarmAssetRepository farmAssetRepository;

    @Autowired
    private FarmActivityLogRepository farmActivityLogRepository;

    @Autowired
    private FarmActivityRepository farmActivityRepository;

    @Autowired
    private FarmConsumptionRepository farmConsumptionRepository;

    @Autowired
    private FarmProductionRepository farmProductionRepository;

    @Autowired
    private FarmSaleRepository farmSaleRepository;

    @Autowired
    private FarmVcaRepository farmVcaRepository;

    @Override
    public void run(String... args) throws Exception {
        initRoles();
        List<Gender> genders = initGenders();
        List<User> users = initUsers(genders);
        List<County> counties = initCounties();
        List<Ward> wards = initWards(counties);
        List<Farm> farms = initFarms(users, counties, wards);
        List<FarmAnimal> farmAnimals = initFarmAnimals(farms);
        List<FarmCrop> farmCrops = initFarmCrops(farms);
        List<FarmAsset> farmAssets = initFarmAssets(farms);
        List<FarmConsumption> farmConsumptions = initFarmConsumptions(farms);
        List<FarmProduction> farmProductions = initFarmProductions(farms);
        List<FarmSale> farmSales = initFarmSales(farms);
        List<FarmVca> farmVcas = initFarmVcas(farms);
        List<FarmActivityLog> farmActivityLogs = initFarmActivityLogs(farms);
        List<FarmActivity> farmActivities = initFarmActivities(farmActivityLogs);
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
        userRepository.deleteAll();
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
                        .password(passwordEncoder.encode("password"))
                        .gender(genders.get(0))
                        .roles(Set.of(farmerRole))
                        .build(),
                User.builder()
                        .firstName("Manager")
                        .lastName("User")
                        .email("manager@fmis.rafiki.co.ke")
                        .password(passwordEncoder.encode("password"))
                        .gender(genders.get(0))
                        .roles(Set.of(farmerRole, managerRole))
                        .build(),
                User.builder()
                        .firstName("Admin")
                        .lastName("User")
                        .email("admin@fmis.rafiki.co.ke")
                        .password(passwordEncoder.encode("password"))
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
        wardRepository.deleteAll();
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

    private List<FarmAnimal> initFarmAnimals(List<Farm> farms) {
        farmAnimalRepository.deleteAll();
        List<FarmAnimal> farmAnimals = List.of(
                FarmAnimal.builder()
                        .name("Cow")
                        .quantity(3)
                        .farm(farms.get(0))
                        .build(),
                FarmAnimal.builder()
                        .name("Goat")
                        .quantity(6)
                        .farm(farms.get(0))
                        .build(),
                FarmAnimal.builder()
                        .name("Sheep")
                        .quantity(6)
                        .farm(farms.get(0))
                        .build(),
                FarmAnimal.builder()
                        .name("Chicken")
                        .quantity(3)
                        .farm(farms.get(0))
                        .build()
        );
        return farmAnimalRepository.saveAll(farmAnimals);
    }

    private List<FarmCrop> initFarmCrops(List<Farm> farms) {
        farmCropRepository.deleteAll();
        List<FarmCrop> farmCrops = List.of(
                FarmCrop.builder().name("Maize").quantity(2).farm(farms.get(0)).build(),
                FarmCrop.builder().name("Goat").quantity(2).farm(farms.get(0)).build(),
                FarmCrop.builder().name("Sheep").quantity(2).farm(farms.get(0)).build(),
                FarmCrop.builder().name("Chicken").quantity(2).farm(farms.get(0)).build()
        );
        return farmCropRepository.saveAll(farmCrops);
    }

    private List<FarmAsset> initFarmAssets(List<Farm> farms) {
        farmAssetRepository.deleteAll();
        List<FarmAsset> farmAssets = List.of(
                FarmAsset.builder().type("Machinery").storageLocation("Tool Shed")
                        .status(AssetStatus.FUNCTIONAL.toString()).farm(farms.get(0)).build(),
                FarmAsset.builder().type("Tool").storageLocation("Tool Shed")
                        .status(AssetStatus.FUNCTIONAL.toString()).farm(farms.get(0)).build(),
                FarmAsset.builder().type("Machinery").storageLocation("Tool Shed")
                        .status(AssetStatus.FUNCTIONAL.toString()).farm(farms.get(0)).build()
        );
        return farmAssetRepository.saveAll(farmAssets);
    }

    private List<FarmConsumption> initFarmConsumptions(List<Farm> farms) {
        farmConsumptionRepository.deleteAll();
        List<FarmConsumption> farmConsumptions = List.of(
                FarmConsumption.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).build(),
                FarmConsumption.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).build(),
                FarmConsumption.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).build(),
                FarmConsumption.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).build()
        );
        return farmConsumptionRepository.saveAll(farmConsumptions);
    }

    private List<FarmProduction> initFarmProductions(List<Farm> farms) {
        farmProductionRepository.deleteAll();
        List<FarmProduction> farmProductions = List.of(
                FarmProduction.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).build(),
                FarmProduction.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).build(),
                FarmProduction.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).build(),
                FarmProduction.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).build()
        );
        return farmProductionRepository.saveAll(farmProductions);
    }

    private List<FarmSale> initFarmSales(List<Farm> farms) {
        farmSaleRepository.deleteAll();
        List<FarmSale> farmSales = List.of(
                FarmSale.builder().date(LocalDate.now()).type(SaleType.CASH.toString())
                        .quantity(BigDecimal.valueOf(10)).farm(farms.get(0)).build(),
                FarmSale.builder().date(LocalDate.now()).type(SaleType.CASH.toString())
                        .quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).build(),
                FarmSale.builder().date(LocalDate.now()).type(SaleType.CREDIT.toString())
                        .quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).build(),
                FarmSale.builder().date(LocalDate.now()).type(SaleType.CREDIT.toString())
                        .quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).build()
        );
        return farmSaleRepository.saveAll(farmSales);
    }

    private List<FarmVca> initFarmVcas(List<Farm> farms) {
        farmVcaRepository.deleteAll();
        List<FarmVca> farmVcas = List.of(
                FarmVca.builder().type("Shelling").description("description")
                        .farm(farms.get(0)).build(),
                FarmVca.builder().type("Peeling").description("description")
                        .farm(farms.get(0)).build(),
                FarmVca.builder().type("Pressing").description("description")
                        .farm(farms.get(0)).build()
        );
        return farmVcaRepository.saveAll(farmVcas);
    }

    private List<FarmActivityLog> initFarmActivityLogs(List<Farm> farms) {
        farmActivityLogRepository.deleteAll();
        List<FarmActivityLog> farmActivityLogs = List.of(
                FarmActivityLog.builder().name("Activity Log 1").year(Year.now())
                        .farm(farms.get(0)).build(),
                FarmActivityLog.builder().name("Activity Log 2").year(Year.now())
                        .farm(farms.get(0)).build(),
                FarmActivityLog.builder().name("Activity Log 3").year(Year.now())
                        .farm(farms.get(0)).build()
        );
        return farmActivityLogRepository.saveAll(farmActivityLogs);
    }

    private List<FarmActivity> initFarmActivities(List<FarmActivityLog> farmActivityLogs) {
        farmActivityRepository.deleteAll();
        List<FarmActivity> farmActivities = List.of(
                FarmActivity.builder().date(LocalDate.now()).activities("Activities")
                        .farmActivityLog(farmActivityLogs.get(0)).build(),
                FarmActivity.builder().date(LocalDate.now()).activities("Activities")
                        .farmActivityLog(farmActivityLogs.get(0)).build(),
                FarmActivity.builder().date(LocalDate.now()).activities("Activities")
                        .farmActivityLog(farmActivityLogs.get(0)).build()
        );
        return farmActivityRepository.saveAll(farmActivities);
    }
}
