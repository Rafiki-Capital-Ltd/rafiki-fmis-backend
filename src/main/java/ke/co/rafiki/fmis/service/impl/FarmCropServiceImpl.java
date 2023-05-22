package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmCrop;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmCropRepository;
import ke.co.rafiki.fmis.service.FarmCropService;
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
public class FarmCropServiceImpl implements FarmCropService {

    private final FarmCropRepository farmCropRepository;
    private final FarmService farmService;
    private final UserService userService;

    public  FarmCropServiceImpl(
            FarmCropRepository farmCropRepository,
            FarmService farmService,
            UserService userService
    ) {
        this.farmCropRepository = farmCropRepository;
        this.farmService = farmService;
        this.userService = userService;
    }



    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public FarmCrop save(FarmCrop farmCrop) throws Exception {
        Farm farm = farmService.findOne(farmCrop.getFarm().getId());
        farmCrop.setFarm(farm);
        return farmCropRepository.save(farmCrop);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmCrop> findAll(
            int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);

        if (isAuthorized(RoleType.MANAGER))
            return farmCropRepository.findAll(pageRequest);

        User user = userService.findOne(getAuthentication().getName());
        return farmCropRepository.findByOwner(user, pageRequest);
    }

    @Override
    @PostAuthorize("hasPermission(returnObject, 'MANAGER')")
    public FarmCrop findOne(UUID id) throws Exception {
        return farmCropRepository.findById(id).orElseThrow(() -> {
            String message = "Farm asset id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmCrop', 'MANAGER')")
    public FarmCrop update(UUID id, FarmCrop farmCrop) throws Exception {
        FarmCrop _farmCrop = this.findOne(id);
        _farmCrop.setName(farmCrop.getName());
        _farmCrop.setQuantity(farmCrop.getQuantity());
        _farmCrop.setDescription(farmCrop.getDescription());
        return farmCropRepository.save(_farmCrop);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmCrop', 'MANAGER')")
    public void delete(UUID id) {
        farmCropRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAll() {
        farmCropRepository.deleteAll();
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGER')")
    public void deleteMany(List<FarmCrop> farmCrops) {
        farmCropRepository.deleteAll(farmCrops);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmCrop> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        Farm _farm = farmService.findOne(farm.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);

        if (isAuthorized(RoleType.MANAGER))
            return farmCropRepository.findByFarm(_farm, pageRequest);

        User owner = userService.findOne(getAuthentication().getName());
        return farmCropRepository.findByOwnerAndFarm(owner, farm, pageRequest);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmCropRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        return farmCropRepository.findByOwner(owner).size();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount(Farm farm) throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmCropRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        Farm _farm = farmService.findOne(farm.getId());
        return farmCropRepository.findByOwnerAndFarm(owner, _farm).size();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getTotal() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmCropRepository.findTotal();

        User owner = userService.findOne(getAuthentication().getName());
        return farmCropRepository.findTotal(owner);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getTotal(Farm farm) throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmCropRepository.findTotal();

        User owner = userService.findOne(getAuthentication().getName());
        Farm _farm = farmService.findOne(farm.getId());
        return farmCropRepository.findTotal(owner, _farm);
    }
}
