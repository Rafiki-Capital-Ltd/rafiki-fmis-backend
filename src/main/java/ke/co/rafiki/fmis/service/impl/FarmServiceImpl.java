package ke.co.rafiki.fmis.service.impl;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.*;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.*;
import ke.co.rafiki.fmis.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Year;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final UserService userService;
    private final FarmLocationRepository farmLocationRepository;
    private final FarmActivityLogRepository farmActivityLogRepository;
    private final CountyRepository countyRepository;
    private final WardRepository wardRepository;

    public FarmServiceImpl(
            FarmRepository farmRepository,
            UserService userService,
            FarmLocationRepository farmLocationRepository,
            FarmActivityLogRepository farmActivityLogRepository,
            CountyRepository countyRepository,
            WardRepository wardRepository
    ) {
        this.farmRepository = farmRepository;
        this.userService = userService;
        this.farmLocationRepository = farmLocationRepository;
        this.farmActivityLogRepository = farmActivityLogRepository;
        this.countyRepository = countyRepository;
        this.wardRepository = wardRepository;
    }

    @Override
    @Transactional
    public Farm save(Farm farm) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(authentication.getName());
        County county = countyRepository.findByName(farm.getCounty().getName()).orElseThrow();
        Ward ward = wardRepository.findByName(farm.getWard().getName()).orElseThrow();
        farm.setCounty(county);
        farm.setWard(ward);
        farm.setOwner(user);
        Farm _farm = farmRepository.save(farm);
        FarmActivityLog farmActivityLog = FarmActivityLog.builder()
                .farm(_farm)
                .name(Year.now() + " Farm Activity Log " + _farm.getName())
                .year(Year.now())
                .build();
        FarmLocation farmLocation = FarmLocation.builder()
                .lat(farm.getLocation().getLat())
                .lng(farm.getLocation().getLng())
                .farm(_farm)
                .build();
        farmActivityLogRepository.save(farmActivityLog);
        farmLocationRepository.save(farmLocation);
        return _farm;
    }

    @Override
    public Page<Farm> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmRepository.findAll(pageRequest);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'Farm', 'ADMIN')")
    public Farm findOne(UUID id) throws Exception {
        return farmRepository.findById(id).orElseThrow(() -> {
            String message = "Farm id " + id + " was not found";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public Farm update(UUID id, Farm farm) throws Exception {
        Farm _farm = this.findOne(id);
        _farm.setName(farm.getName());
        _farm.setSize(farm.getSize());
        _farm.setCounty(farm.getCounty());
        _farm.setWard(farm.getWard());
        _farm.setNearestShoppingCenter(farm.getNearestShoppingCenter());
        _farm.setLocation(farm.getLocation());
        return farmRepository.save(_farm);
    }

    @Override
    public void delete(UUID id) {
        farmRepository.deleteById(id);
    }

    @Override
    public void deleteMany(List<Farm> farms) {
        farmRepository.deleteAll(farms);
    }

    @Override
    public void deleteAll() {
        farmRepository.deleteAll();
    }

    @Override
    public Page<Farm> findByFarmer(
            Principal farmer, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        User _farmer = userService.findOne(farmer.getName());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmRepository.findByOwner(_farmer, pageRequest);
    }
}
