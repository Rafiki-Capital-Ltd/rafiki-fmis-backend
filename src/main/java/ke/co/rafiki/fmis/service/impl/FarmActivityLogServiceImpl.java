package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmActivityLog;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmActivityLogRepository;
import ke.co.rafiki.fmis.service.FarmActivityLogService;
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
public class FarmActivityLogServiceImpl implements FarmActivityLogService {

    private final FarmActivityLogRepository farmActivityLogRepository;
    private final FarmService farmService;
    private final UserService userService;

    public FarmActivityLogServiceImpl(
            FarmActivityLogRepository farmActivityLogRepository,
            FarmService farmService,
            UserService userService
    ) {
        this.farmActivityLogRepository = farmActivityLogRepository;
        this.farmService = farmService;
        this.userService = userService;
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public FarmActivityLog save(FarmActivityLog farmActivityLog) throws Exception {
        Farm farm = farmService.findOne(farmActivityLog.getFarm().getId());
        farmActivityLog.setFarm(farm);
        return farmActivityLogRepository.save(farmActivityLog);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmActivityLog> findAll(int page, int size, String sort, String sortDirection) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);

        if (isAuthorized(RoleType.MANAGER))
            return farmActivityLogRepository.findAll(pageRequest);

        User user = userService.findOne(getAuthentication().getName());
        return farmActivityLogRepository.findByOwner(user, pageRequest);
    }

    @Override
    @PostAuthorize("hasPermission(returnObject, 'MANAGER')")
    public FarmActivityLog findOne(UUID id) throws Exception {
        return farmActivityLogRepository.findById(id).orElseThrow(() -> {
            String message = "Farm activity log id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmActivityLog', 'MANAGER')")
    public FarmActivityLog update(UUID id, FarmActivityLog farmActivityLog) throws Exception {
        FarmActivityLog _farmActivityLog = this.findOne(id);
        _farmActivityLog.setYear(farmActivityLog.getYear());
        _farmActivityLog.setName(farmActivityLog.getName());
        return farmActivityLogRepository.save(_farmActivityLog);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmActivityLog', 'MANAGER')")
    public void delete(UUID id) {
        farmActivityLogRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGER')")
    public void deleteMany(List<FarmActivityLog> farmActivities) {
        farmActivityLogRepository.deleteAll(farmActivities);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAll() {
        farmActivityLogRepository.deleteAll();
    }
}
