package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmInput;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmInputRepository;
import ke.co.rafiki.fmis.service.FarmInputService;
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
import static ke.co.rafiki.fmis.misc.HelperMethods.*;

@Slf4j
@Service
public class FarmInputServiceImpl implements FarmInputService {

    private final FarmInputRepository farmInputRepository;
    private final FarmService farmService;
    private final UserService userService;

    public  FarmInputServiceImpl(
            FarmInputRepository farmInputRepository,
            FarmService farmService,
            UserService userService
    ) {
        this.farmInputRepository = farmInputRepository;
        this.farmService = farmService;
        this.userService = userService;
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmInput> findAll(
            int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        PageRequest pageRequest = getPageRequest(page, size, sort, sortDirection);

        if (isAuthorized(RoleType.MANAGER))
            return farmInputRepository.findAll(pageRequest);

        User owner = userService.findOne(getAuthentication().getName());
        return farmInputRepository.findByOwner(owner, pageRequest);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmInput> findAll(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        PageRequest pageRequest = getPageRequest(page, size, sort, sortDirection);

        if (isAuthorized(RoleType.MANAGER))
            return farmInputRepository.findAll(pageRequest);

        Farm _farm = farmService.findOne(farm.getId());
        User owner = userService.findOne(getAuthentication().getName());
        return farmInputRepository.findByOwnerAndFarm(owner, _farm, pageRequest);
    }

    @Override
    @PostAuthorize("hasPermission(returnObject, 'FARMER')")
    public FarmInput findOne(UUID id) throws Exception {
        return farmInputRepository.findById(id).orElseThrow(() -> {
            String message = "Farm asset id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public FarmInput save(FarmInput farmInput) throws Exception {
        Farm farm = farmService.findOne(farmInput.getFarm().getId());
        User owner = userService.findOne(getAuthentication().getName());
        farmInput.setFarm(farm);
        farmInput.setOwner(owner);
        return farmInputRepository.save(farmInput);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmInput', 'MANAGER')")
    public FarmInput update(UUID id, FarmInput farmInput) throws Exception {
        FarmInput _farmInput = this.findOne(id);
        _farmInput.setType(farmInput.getType());
        _farmInput.setQuantity(farmInput.getQuantity());
        _farmInput.setDescription(farmInput.getDescription());
        return farmInputRepository.save(_farmInput);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmInput', 'MANAGER')")
    public void delete(UUID id) {
        farmInputRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAll() {
        farmInputRepository.deleteAll();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public void deleteMany(List<FarmInput> farmInputs) {
        farmInputRepository.deleteAll(farmInputs);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmInputRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        return farmInputRepository.findByOwner(owner).size();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount(Farm farm) throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmInputRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        Farm _farm = farmService.findOne(farm.getId());
        return farmInputRepository.findByOwnerAndFarm(owner, _farm).size();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getTotal() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmInputRepository.findTotal();

        User owner = userService.findOne(getAuthentication().getName());
        return farmInputRepository.findTotal(owner);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getTotal(Farm farm) throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmInputRepository.findTotal();

        User owner = userService.findOne(getAuthentication().getName());
        Farm _farm = farmService.findOne(farm.getId());
        return farmInputRepository.findTotal(owner, _farm);
    }
}
