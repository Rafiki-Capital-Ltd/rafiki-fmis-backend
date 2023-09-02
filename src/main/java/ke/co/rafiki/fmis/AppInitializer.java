package ke.co.rafiki.fmis;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.*;
import ke.co.rafiki.fmis.domain.enums.AssetStatus;
import ke.co.rafiki.fmis.domain.enums.PurchaseType;
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
    private ConstituencyRepository constituencyRepository;

    @Autowired
    private SubCountyRepository subCountyRepository;

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private FarmAnimalRepository farmAnimalRepository;

    @Autowired
    private FarmCropRepository farmCropRepository;

    @Autowired
    private FarmInputRepository farmInputRepository;

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
    private FarmPurchaseRepository farmPurchaseRepository;

    @Autowired
    private FarmExpenseRepository farmExpenseRepository;

    @Autowired
    private FarmVcaRepository farmVcaRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public void run(String... args) throws Exception {
        refreshTokenRepository.deleteAll();
        List<Role> roles = initRoles();
        List<User> users = initUsers();
        List<Farm> farms = initFarms(users);
        List<FarmAnimal> farmAnimals = initFarmAnimals(farms);
        List<FarmCrop> farmCrops = initFarmCrops(farms);
        List<FarmAsset> farmAssets = initFarmAssets(farms);
        List<FarmInput> farmInputs = initFarmInputs(farms);
        List<FarmConsumption> farmConsumptions = initFarmConsumptions(farms);
        List<FarmProduction> farmProductions = initFarmProductions(farms);
        List<FarmSale> farmSales = initFarmSales(farms);
        List<FarmPurchase> farmPurchases = initFarmPurchases(farms);
        List<FarmExpense> farmExpenses = initFarmExpenses(farms);
        List<FarmVca> farmVcas = initFarmVcas(farms);
        List<FarmActivityLog> farmActivityLogs = initFarmActivityLogs(farms);
        List<FarmActivity> farmActivities = initFarmActivities(farmActivityLogs);
    }

    @Transactional
    public List<Role> initRoles() {
        roleRepository.findAll()
                .forEach(role -> roleRepository.disassociateRoleFromUser(role.getId()));
        roleRepository.deleteAll();
        return roleRepository.saveAll(
                Arrays.stream(RoleType.values())
                    .map(type -> Role.builder().name(type.toString()).build())
                    .collect(Collectors.toSet())
        );
    }

    @Transactional
    public List<User> initUsers() throws Exception {
        deleteUserAssociations(userRepository.findAll());
        userRepository.deleteAll();
        Role farmerRole = roleRepository.findByName(RoleType.FARMER.toString())
                .orElseThrow(NotFoundException::new);
        Role managerRole = roleRepository.findByName(RoleType.MANAGER.toString())
                .orElseThrow(NotFoundException::new);
        Role adminRole = roleRepository.findByName(RoleType.ADMIN.toString())
                .orElseThrow(NotFoundException::new);
        List<User> users = List.of(
                User.builder()
                        .firstName("Ernest")
                        .lastName("Wambua")
                        .email("ernestwambua2@gmail.com")
                        .password(passwordEncoder.encode("password"))
                        .roles(Set.of(farmerRole))
                        .build(),
                User.builder()
                        .firstName("Daniel")
                        .lastName("Gitari")
                        .email("dan@dan.com")
                        .password(passwordEncoder.encode("password"))
                        .roles(Set.of(farmerRole))
                        .build(),
                User.builder()
                        .firstName("Farmer")
                        .lastName("User")
                        .email("farmer@fmis.rafiki.co.ke")
                        .password(passwordEncoder.encode("password"))
                        .roles(Set.of(farmerRole))
                        .build(),
                User.builder()
                        .firstName("Manager")
                        .lastName("User")
                        .email("manager@fmis.rafiki.co.ke")
                        .password(passwordEncoder.encode("password"))
                        .roles(Set.of(farmerRole, managerRole))
                        .build(),
                User.builder()
                        .firstName("Admin")
                        .lastName("User")
                        .email("admin@fmis.rafiki.co.ke")
                        .password(passwordEncoder.encode("password"))
                        .roles(Set.of(farmerRole, managerRole, adminRole))
                        .build()
        );
        roleRepository.saveAll(List.of(farmerRole, managerRole, adminRole));
        return userRepository.saveAll(users);
    }

    @Transactional
    public List<Farm> initFarms(List<User> users) {
        deleteFarmAssociations(farmRepository.findAll());
        farmRepository.deleteAll();
        County county = countyRepository.findById(1).get();
        Constituency constituency1 = constituencyRepository.findByCounty(county).get(0);
        Constituency constituency2 = constituencyRepository.findByCounty(county).get(1);
        SubCounty subCounty1 = subCountyRepository.findByConstituency(constituency1).get(0);
        SubCounty subCounty2 = subCountyRepository.findByConstituency(constituency2).get(0);
        List<Farm> farms = List.of(
                Farm.builder()
                        .name("Test Farm 1")
                        .owner(users.get(0))
                        .size(BigDecimal.valueOf(12.0))
                        .county(county)
                        .constituency(constituency1)
                        .subCounty(subCounty1)
                        .build(),
                Farm.builder()
                        .name("Test Farm 2")
                        .owner(users.get(1))
                        .size(BigDecimal.valueOf(10.0))
                        .county(county)
                        .constituency(constituency2)
                        .subCounty(subCounty2)
                        .build()
        );
        return farmRepository.saveAll(farms);
    }

    @Transactional
    public List<FarmAnimal> initFarmAnimals(List<Farm> farms) {
        farmAnimalRepository.deleteAll();
        User owner = userRepository.findByEmail("farmer@fmis.rafiki.co.ke").orElseThrow();
        List<FarmAnimal> farmAnimals = List.of(
                FarmAnimal.builder()
                        .type("Cow")
                        .quantity(3)
                        .farm(farms.get(0))
                        .owner(owner)
                        .build(),
                FarmAnimal.builder()
                        .type("Goat")
                        .quantity(6)
                        .farm(farms.get(0))
                        .owner(owner)
                        .build(),
                FarmAnimal.builder()
                        .type("Sheep")
                        .quantity(6)
                        .farm(farms.get(0))
                        .owner(owner)
                        .build(),
                FarmAnimal.builder()
                        .type("Chicken")
                        .quantity(3)
                        .farm(farms.get(0))
                        .owner(owner)
                        .build()
        );
        return farmAnimalRepository.saveAll(farmAnimals);
    }

    @Transactional
    public List<FarmCrop> initFarmCrops(List<Farm> farms) {
        farmCropRepository.deleteAll();
        User owner = userRepository.findByEmail("farmer@fmis.rafiki.co.ke").orElseThrow();
        List<FarmCrop> farmCrops = List.of(
                FarmCrop.builder().type("Maize").quantity(2)
                        .owner(owner).farm(farms.get(0)).build(),
                FarmCrop.builder().type("Goat").quantity(2)
                        .owner(owner).farm(farms.get(0)).build(),
                FarmCrop.builder().type("Sheep").quantity(2)
                        .owner(owner).farm(farms.get(0)).build(),
                FarmCrop.builder().type("Chicken").quantity(2)
                        .owner(owner).farm(farms.get(0)).build()
        );
        return farmCropRepository.saveAll(farmCrops);
    }

    @Transactional
    public List<FarmInput> initFarmInputs(List<Farm> farms) {
        farmInputRepository.deleteAll();
        User owner = userRepository.findByEmail("farmer@fmis.rafiki.co.ke").orElseThrow();
        List<FarmInput> farmInputs = List.of(
                FarmInput.builder().type("Pesticides").quantity(2)
                        .owner(owner).farm(farms.get(0)).build(),
                FarmInput.builder().type("Insecticides").quantity(2)
                        .owner(owner).farm(farms.get(0)).build(),
                FarmInput.builder().type("Herbicides").quantity(2)
                        .owner(owner).farm(farms.get(0)).build(),
                FarmInput.builder().type("Seedlings").quantity(2)
                        .owner(owner).farm(farms.get(0)).build()
        );
        return farmInputRepository.saveAll(farmInputs);
    }

    @Transactional
    public List<FarmAsset> initFarmAssets(List<Farm> farms) {
        farmAssetRepository.deleteAll();
        User owner = userRepository.findByEmail("farmer@fmis.rafiki.co.ke").orElseThrow();
        List<FarmAsset> farmAssets = List.of(
                FarmAsset.builder().type("Machinery").storageLocation("Tool Shed")
                        .status(AssetStatus.FUNCTIONAL.toString()).farm(farms.get(0))
                        .owner(owner).build(),
                FarmAsset.builder().type("Tool").storageLocation("Tool Shed")
                        .status(AssetStatus.FUNCTIONAL.toString()).farm(farms.get(0))
                        .owner(owner).build(),
                FarmAsset.builder().type("Machinery").storageLocation("Tool Shed")
                        .status(AssetStatus.FUNCTIONAL.toString()).farm(farms.get(0))
                        .owner(owner).build()
        );
        return farmAssetRepository.saveAll(farmAssets);
    }

    @Transactional
    public List<FarmConsumption> initFarmConsumptions(List<Farm> farms) {
        farmConsumptionRepository.deleteAll();
        User owner = userRepository.findByEmail("farmer@fmis.rafiki.co.ke").orElseThrow();
        List<FarmConsumption> farmConsumptions = List.of(
                FarmConsumption.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).owner(owner).build(),
                FarmConsumption.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).owner(owner).build(),
                FarmConsumption.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).owner(owner).build(),
                FarmConsumption.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).owner(owner).build()
        );
        return farmConsumptionRepository.saveAll(farmConsumptions);
    }

    @Transactional
    public List<FarmProduction> initFarmProductions(List<Farm> farms) {
        farmProductionRepository.deleteAll();
        User owner = userRepository.findByEmail("farmer@fmis.rafiki.co.ke").orElseThrow();
        List<FarmProduction> farmProductions = List.of(
                FarmProduction.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).owner(owner).build(),
                FarmProduction.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).owner(owner).build(),
                FarmProduction.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).owner(owner).build(),
                FarmProduction.builder().date(LocalDate.now()).quantity(BigDecimal.valueOf(10))
                        .farm(farms.get(0)).owner(owner).build()
        );
        return farmProductionRepository.saveAll(farmProductions);
    }

    @Transactional
    public List<FarmSale> initFarmSales(List<Farm> farms) {
        farmSaleRepository.deleteAll();
        User owner = userRepository.findByEmail("farmer@fmis.rafiki.co.ke").orElseThrow();
        List<FarmSale> farmSales = List.of(
                FarmSale.builder().date(LocalDate.now()).type(SaleType.CASH.toString())
                        .quantity(BigDecimal.valueOf(10)).farm(farms.get(0))
                        .owner(owner).build(),
                FarmSale.builder().date(LocalDate.now()).type(SaleType.CASH.toString())
                        .quantity(BigDecimal.valueOf(10)).farm(farms.get(0))
                        .owner(owner).build(),
                FarmSale.builder().date(LocalDate.now()).type(SaleType.CREDIT.toString())
                        .quantity(BigDecimal.valueOf(10)).farm(farms.get(0))
                        .owner(owner).build(),
                FarmSale.builder().date(LocalDate.now()).type(SaleType.CREDIT.toString())
                        .quantity(BigDecimal.valueOf(10)).farm(farms.get(0))
                        .owner(owner).build()
        );
        return farmSaleRepository.saveAll(farmSales);
    }

    @Transactional
    public List<FarmPurchase> initFarmPurchases(List<Farm> farms) {
        farmPurchaseRepository.deleteAll();
        User owner = userRepository.findByEmail("farmer@fmis.rafiki.co.ke").orElseThrow();
        List<FarmPurchase> farmPurchases = List.of(
                FarmPurchase.builder().date(LocalDate.now()).type(PurchaseType.CASH.toString())
                        .quantity(BigDecimal.valueOf(10)).farm(farms.get(0))
                        .owner(owner).build(),
                FarmPurchase.builder().date(LocalDate.now()).type(PurchaseType.CASH.toString())
                        .quantity(BigDecimal.valueOf(10)).farm(farms.get(0))
                        .owner(owner).build(),
                FarmPurchase.builder().date(LocalDate.now()).type(PurchaseType.CREDIT.toString())
                        .quantity(BigDecimal.valueOf(10)).farm(farms.get(0))
                        .owner(owner).build(),
                FarmPurchase.builder().date(LocalDate.now()).type(PurchaseType.CREDIT.toString())
                        .quantity(BigDecimal.valueOf(10)).farm(farms.get(0))
                        .owner(owner).build()
        );
        return farmPurchaseRepository.saveAll(farmPurchases);
    }

    @Transactional
    public List<FarmExpense> initFarmExpenses(List<Farm> farms) {
        farmExpenseRepository.deleteAll();
        User owner = userRepository.findByEmail("farmer@fmis.rafiki.co.ke").orElseThrow();
        List<FarmExpense> farmExpenses = List.of(
                FarmExpense.builder().date(LocalDate.now()).type(PurchaseType.CASH.toString())
                        .amount(new BigDecimal(100)).farm(farms.get(0))
                        .owner(owner).build(),
                FarmExpense.builder().date(LocalDate.now()).type(PurchaseType.CASH.toString())
                        .amount(new BigDecimal(100)).farm(farms.get(0))
                        .owner(owner).build(),
                FarmExpense.builder().date(LocalDate.now()).type(PurchaseType.CREDIT.toString())
                        .amount(new BigDecimal(100)).farm(farms.get(0))
                        .owner(owner).build(),
                FarmExpense.builder().date(LocalDate.now()).type(PurchaseType.CREDIT.toString())
                        .amount(new BigDecimal(100)).farm(farms.get(0))
                        .owner(owner).build()
        );
        return farmExpenseRepository.saveAll(farmExpenses);
    }

    @Transactional
    public List<FarmVca> initFarmVcas(List<Farm> farms) {
        farmVcaRepository.deleteAll();
        User owner = userRepository.findByEmail("farmer@fmis.rafiki.co.ke").orElseThrow();
        List<FarmVca> farmVcas = List.of(
                FarmVca.builder().type("Shelling").description("description")
                        .farm(farms.get(0)).owner(owner).build(),
                FarmVca.builder().type("Peeling").description("description")
                        .farm(farms.get(0)).owner(owner).build(),
                FarmVca.builder().type("Pressing").description("description")
                        .farm(farms.get(0)).owner(owner).build()
        );
        return farmVcaRepository.saveAll(farmVcas);
    }

    @Transactional
    public List<FarmActivityLog> initFarmActivityLogs(List<Farm> farms) {
        farmActivityLogRepository.deleteAll();
        User owner = userRepository.findByEmail("farmer@fmis.rafiki.co.ke").orElseThrow();
        List<FarmActivityLog> farmActivityLogs = List.of(
                FarmActivityLog.builder().name("Activity Log 1").year(Year.now())
                        .farm(farms.get(0)).owner(owner).build(),
                FarmActivityLog.builder().name("Activity Log 2").year(Year.now())
                        .farm(farms.get(0)).owner(owner).build(),
                FarmActivityLog.builder().name("Activity Log 3").year(Year.now())
                        .farm(farms.get(0)).owner(owner).build()
        );
        return farmActivityLogRepository.saveAll(farmActivityLogs);
    }

    @Transactional
    public List<FarmActivity> initFarmActivities(List<FarmActivityLog> farmActivityLogs) {
        farmActivityRepository.deleteAll();
        User owner = userRepository.findByEmail("farmer@fmis.rafiki.co.ke").orElseThrow();
        List<FarmActivity> farmActivities = List.of(
                FarmActivity.builder().date(LocalDate.now()).activities("Activities")
                        .farmActivityLog(farmActivityLogs.get(0))
                        .owner(owner)
                        .build(),
                FarmActivity.builder().date(LocalDate.now()).activities("Activities")
                        .farmActivityLog(farmActivityLogs.get(0))
                        .owner(owner)
                        .build(),
                FarmActivity.builder().date(LocalDate.now()).activities("Activities")
                        .farmActivityLog(farmActivityLogs.get(0))
                        .owner(owner)
                        .build()
        );
        return farmActivityRepository.saveAll(farmActivities);
    }

    @Transactional
    public void deleteUserAssociations(List<User> users) {
        users.forEach(user -> {
            farmRepository.disassociateFromOwner(user);
            farmAssetRepository.disassociateFromOwner(user);
            farmInputRepository.disassociateFromOwner(user);
            farmActivityLogRepository.disassociateFromOwner(user);
            farmActivityRepository.disassociateFromOwner(user);
            farmAnimalRepository.disassociateFromOwner(user);
            farmCropRepository.disassociateFromOwner(user);
            farmConsumptionRepository.disassociateFromOwner(user);
            farmProductionRepository.disassociateFromOwner(user);
            farmSaleRepository.disassociateFromOwner(user);
            farmPurchaseRepository.disassociateFromOwner(user);
            farmExpenseRepository.disassociateFromOwner(user);
            farmVcaRepository.disassociateFromOwner(user);
            userRepository.disassociateRoleFromUser(user.getId());
        });
    }

    @Transactional
    public void deleteFarmAssociations(List<Farm> farms) {
        farms.forEach(farm -> {
            farmAssetRepository.disassociateFromFarm(farm);
            farmInputRepository.disassociateFromFarm(farm);
            farmActivityLogRepository.disassociateFromFarm(farm);
            farmAnimalRepository.disassociateFromFarm(farm);
            farmCropRepository.disassociateFromFarm(farm);
            farmConsumptionRepository.disassociateFromFarm(farm);
            farmProductionRepository.disassociateFromFarm(farm);
            farmSaleRepository.disassociateFromFarm(farm);
            farmPurchaseRepository.disassociateFromFarm(farm);
            farmExpenseRepository.disassociateFromFarm(farm);
            farmVcaRepository.disassociateFromFarm(farm);
        });
    }
}
