package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmConsumption;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmConsumptionRepository;
import ke.co.rafiki.fmis.service.FarmConsumptionService;
import ke.co.rafiki.fmis.service.FarmService;
import ke.co.rafiki.fmis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static ke.co.rafiki.fmis.misc.HelperMethods.getAuthentication;
import static ke.co.rafiki.fmis.misc.HelperMethods.isAuthorized;
import static ke.co.rafiki.fmis.misc.HelperMethods.*;

@Slf4j
@Service
public class FarmConsumptionServiceImpl implements FarmConsumptionService {

    private final FarmConsumptionRepository farmConsumptionRepository;
    private final FarmService farmService;
    private final UserService userService;

    public  FarmConsumptionServiceImpl(
            FarmConsumptionRepository farmConsumptionRepository,
            FarmService farmService,
            UserService userService
    ) {
        this.farmConsumptionRepository = farmConsumptionRepository;
        this.farmService = farmService;
        this.userService = userService;
    }



    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public FarmConsumption save(FarmConsumption farmConsumption) throws Exception {
        Farm farm = farmService.findOne(farmConsumption.getFarm().getId());
        farmConsumption.setFarm(farm);
        return farmConsumptionRepository.save(farmConsumption);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmConsumption> findAll(int page, int size, String sort,
                                         String sortDirection) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);

        if (isAuthorized(RoleType.MANAGER))
            return farmConsumptionRepository.findAll(pageRequest);

        User owner = userService.findOne(getAuthentication().getName());
        return farmConsumptionRepository.findByOwner(owner, pageRequest);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmConsumption> findAll(Farm farm, int page, int size, String sort,
                                         String sortDirection) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);

        if (isAuthorized(RoleType.MANAGER))
            return farmConsumptionRepository.findAll(pageRequest);

        Farm _farm = farmService.findOne(farm.getId());
        User owner = userService.findOne(getAuthentication().getName());
        return farmConsumptionRepository.findByOwnerAndFarm(owner, _farm, pageRequest);
    }

    @Override
    @PostAuthorize("hasPermission(returnObject, 'MANAGER')")
    public FarmConsumption findOne(UUID id) throws Exception {
        return farmConsumptionRepository.findById(id).orElseThrow(() -> {
            String message = "Farm asset id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmConsumption', 'MANAGER')")
    public FarmConsumption update(UUID id, FarmConsumption farmConsumption) throws Exception {
        FarmConsumption _farmConsumption = this.findOne(id);
        _farmConsumption.setDate(farmConsumption.getDate());
        _farmConsumption.setQuantity(farmConsumption.getQuantity());
        _farmConsumption.setDescription(farmConsumption.getDescription());
        return farmConsumptionRepository.save(_farmConsumption);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmConsumption', 'MANAGER')")
    public void delete(UUID id) {
        farmConsumptionRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAll() {
        farmConsumptionRepository.deleteAll();
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGER')")
    public void deleteMany(List<FarmConsumption> farmConsumptions) {
        farmConsumptionRepository.deleteAll(farmConsumptions);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmConsumption> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        Farm _farm = farmService.findOne(farm.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmConsumptionRepository.findByFarm(_farm, pageRequest);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmConsumptionRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        return farmConsumptionRepository.findByOwner(owner).size();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount(Farm farm) throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmConsumptionRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        Farm _farm = farmService.findOne(farm.getId());
        return farmConsumptionRepository.findByOwnerAndFarm(owner, _farm).size();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public BigDecimal getTotal() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmConsumptionRepository.findTotal().orElse(BigDecimal.ZERO);

        User owner = userService.findOne(getAuthentication().getName());
        return farmConsumptionRepository.findTotal(owner).orElse(BigDecimal.ZERO);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public BigDecimal getTotal(Farm farm) throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmConsumptionRepository.findTotal().orElse(BigDecimal.ZERO);

        User owner = userService.findOne(getAuthentication().getName());
        Farm _farm = farmService.findOne(farm.getId());
        return farmConsumptionRepository.findTotal(owner, _farm).orElse(BigDecimal.ZERO);
    }
}
