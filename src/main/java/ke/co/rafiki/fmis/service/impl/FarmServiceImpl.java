package ke.co.rafiki.fmis.service.impl;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.*;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.*;
import ke.co.rafiki.fmis.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import static ke.co.rafiki.fmis.misc.HelperMethods.*;

@Slf4j
@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final UserService userService;
    private final FarmLocationRepository farmLocationRepository;

    public FarmServiceImpl(
            FarmRepository farmRepository,
            UserService userService,
            FarmLocationRepository farmLocationRepository
    ) {
        this.farmRepository = farmRepository;
        this.userService = userService;
        this.farmLocationRepository = farmLocationRepository;
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('FARMER')")
    public Farm save(Farm farm) throws Exception {
        User user = userService.findOne(getAuthentication().getName());
        farm.setOwner(user);
        Farm _farm = farmRepository.save(farm);

        if (farm.getLocation() != null) {
            FarmLocation farmLocation = FarmLocation.builder()
                    .lat(farm.getLocation().getLat())
                    .lng(farm.getLocation().getLng())
                    .farm(_farm)
                    .build();
            farmLocationRepository.save(farmLocation);
        }

        return _farm;
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<Farm> findAll(int page, int size, String sort, String sortDirection) throws Exception {
        PageRequest pageRequest = getPageRequest(page, size, sort, sortDirection);

        boolean isAuthorized = isAuthorized(RoleType.MANAGER);
        if (isAuthorized) return farmRepository.findAll(pageRequest);

        User owner = userService.findOne(getAuthentication().getName());
        return farmRepository.findByOwner(owner, pageRequest);
    }

    @Override
    @PostAuthorize("hasPermission(returnObject, 'MANAGER')")
    public Farm findOne(UUID id) throws Exception {
        return farmRepository.findById(id).orElseThrow(() -> {
            String message = "Farm id " + id + " was not found";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'Farm', 'MANAGER')")
    public Farm update(UUID id, Farm farm) throws Exception {
        Farm _farm = this.findOne(id);
        _farm.setName(farm.getName());
        _farm.setSize(farm.getSize());
        return farmRepository.save(_farm);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'Farm', 'MANAGER')")
    public void delete(UUID id) {
        farmRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public void deleteMany(List<Farm> farms) {
        farmRepository.deleteAll(farms);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAll() {
        farmRepository.deleteAll();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        return farmRepository.findByOwner(owner).size();
    }
}
