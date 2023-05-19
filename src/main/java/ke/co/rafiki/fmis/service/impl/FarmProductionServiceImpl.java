package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmProduction;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmProductionRepository;
import ke.co.rafiki.fmis.service.FarmProductionService;
import ke.co.rafiki.fmis.service.FarmService;
import ke.co.rafiki.fmis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static ke.co.rafiki.fmis.misc.HelperMethods.getAuthentication;
import static ke.co.rafiki.fmis.misc.HelperMethods.isAuthorized;
import static ke.co.rafiki.fmis.misc.HelperMethods.*;

@Slf4j
@Service
public class FarmProductionServiceImpl implements FarmProductionService {

    private final FarmProductionRepository farmProductionRepository;
    private final FarmService farmService;
    private final UserService userService;

    public  FarmProductionServiceImpl(
            FarmProductionRepository farmProductionRepository,
            FarmService farmService,
            UserService userService
    ) {
        this.farmProductionRepository = farmProductionRepository;
        this.farmService = farmService;
        this.userService = userService;
    }



    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public FarmProduction save(FarmProduction farmProduction) throws Exception {
        Farm farm = farmService.findOne(farmProduction.getFarm().getId());
        farmProduction.setFarm(farm);
        return farmProductionRepository.save(farmProduction);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmProduction> findAll(
            int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);

        if (isAuthorized(RoleType.MANAGER))
            return farmProductionRepository.findAll(pageRequest);

        User owner = userService.findOne(getAuthentication().getName());
        return farmProductionRepository.findByOwner(owner, pageRequest);
    }

    @Override
    @PostAuthorize("hasPermission(returnObject, 'MANAGER')")
    public FarmProduction findOne(UUID id) throws Exception {
        return farmProductionRepository.findById(id).orElseThrow(() -> {
            String message = "Farm asset id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmProduction', 'MANAGER')")
    public FarmProduction update(UUID id, FarmProduction farmProduction) throws Exception {
        FarmProduction _farmProduction = this.findOne(id);
        _farmProduction.setDate(farmProduction.getDate());
        _farmProduction.setQuantity(farmProduction.getQuantity());
        _farmProduction.setDescription(farmProduction.getDescription());
        return farmProductionRepository.save(_farmProduction);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmProduction', 'MANAGER')")
    public void delete(UUID id) {
        farmProductionRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAll() {
        farmProductionRepository.deleteAll();
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGER')")
    public void deleteMany(List<FarmProduction> farmProductions) {
        farmProductionRepository.deleteAll(farmProductions);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmProduction> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        Farm _farm = farmService.findOne(farm.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmProductionRepository.findByFarm(_farm, pageRequest);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmProductionRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        return farmProductionRepository.findByOwner(owner).size();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount(Farm farm) throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmProductionRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        Farm _farm = farmService.findOne(farm.getId());
        return farmProductionRepository.findByOwnerAndFarm(owner, _farm).size();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getTotal() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmProductionRepository.findTotal();

        User owner = userService.findOne(getAuthentication().getName());
        return farmProductionRepository.findTotal(owner);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getTotal(Farm farm) throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmProductionRepository.findTotal();

        User owner = userService.findOne(getAuthentication().getName());
        Farm _farm = farmService.findOne(farm.getId());
        return farmProductionRepository.findTotal(owner, _farm);
    }
}
