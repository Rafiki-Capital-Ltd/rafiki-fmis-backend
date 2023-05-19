package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.FarmActivity;
import ke.co.rafiki.fmis.domain.FarmActivityLog;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmActivityRepository;
import ke.co.rafiki.fmis.service.FarmActivityLogService;
import ke.co.rafiki.fmis.service.FarmActivityService;
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
public class FarmActivityServiceImpl implements FarmActivityService {

    private final FarmActivityRepository farmActivityRepository;
    private final FarmActivityLogService farmActivityLogService;
    private final UserService userService;

    public FarmActivityServiceImpl(
            FarmActivityRepository farmActivityRepository,
            FarmActivityLogService farmActivityLogService,
            UserService userService
    ) {
        this.farmActivityRepository = farmActivityRepository;
        this.farmActivityLogService = farmActivityLogService;
        this.userService = userService;
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public FarmActivity save(FarmActivity farmActivity) throws Exception {
        FarmActivityLog farmActivityLog = farmActivityLogService.findOne(farmActivity
                .getFarmActivityLog().getId());
        farmActivity.setFarmActivityLog(farmActivityLog);
        return farmActivityRepository.save(farmActivity);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmActivity> findAll(int page, int size, String sort, String sortDirection) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);

        if (isAuthorized(RoleType.MANAGER))
            return farmActivityRepository.findAll(pageRequest);

        User owner = userService.findOne(getAuthentication().getName());
        return farmActivityRepository.findByOwner(owner, pageRequest);
    }

    @Override
    @PostAuthorize("hasPermission(returnObject, 'MANAGER')")
    public FarmActivity findOne(UUID id) throws Exception {
        return farmActivityRepository.findById(id).orElseThrow(() -> {
            String message = "Farm activity " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmActivity', 'MANAGER')")
    public FarmActivity update(UUID id, FarmActivity farmActivity) throws Exception {
        FarmActivity _farmActivity = this.findOne(id);
        _farmActivity.setDate(farmActivity.getDate());
        _farmActivity.setActivities(farmActivity.getActivities());
        return farmActivityRepository.save(_farmActivity);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmActivity', 'MANAGER')")
    public void delete(UUID id) {
        farmActivityRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAll() {
        farmActivityRepository.deleteAll();
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGER')")
    public void deleteMany(List<FarmActivity> farmActivities) {
        farmActivityRepository.deleteAll(farmActivities);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmActivity> findByFarmActivityLog(
            FarmActivityLog farmActivityLog, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        FarmActivityLog _farmActivityLog = farmActivityLogService.findOne(farmActivityLog.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmActivityRepository.findByFarmActivityLog(_farmActivityLog, pageRequest);
    }
}
