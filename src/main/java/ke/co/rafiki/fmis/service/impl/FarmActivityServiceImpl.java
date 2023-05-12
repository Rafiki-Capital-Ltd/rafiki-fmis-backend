package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmActivity;
import ke.co.rafiki.fmis.domain.FarmActivityLog;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmActivityRepository;
import ke.co.rafiki.fmis.service.FarmActivityLogService;
import ke.co.rafiki.fmis.service.FarmActivityService;
import ke.co.rafiki.fmis.service.FarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FarmActivityServiceImpl implements FarmActivityService {

    private final FarmActivityRepository farmActivityRepository;
    private final FarmActivityLogService farmActivityLogService;
    private final FarmService farmService;

    public FarmActivityServiceImpl(
            FarmActivityRepository farmActivityRepository,
            FarmActivityLogService farmActivityLogService,
            FarmService farmService
    ) {
        this.farmActivityRepository = farmActivityRepository;
        this.farmActivityLogService = farmActivityLogService;
        this.farmService = farmService;
    }

    @Override
    public FarmActivity save(FarmActivity farmActivity) throws Exception {
        FarmActivityLog farmActivityLog = farmActivityLogService.findOne(farmActivity
                .getFarmActivityLog().getId());
        farmActivity.setFarmActivityLog(farmActivityLog);
        return farmActivityRepository.save(farmActivity);
    }

    @Override
    public Page<FarmActivity> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmActivityRepository.findAll(pageRequest);
    }

    @Override
    public FarmActivity findOne(UUID id) throws Exception {
        return farmActivityRepository.findById(id).orElseThrow(() -> {
            String message = "Farm activity " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public FarmActivity update(UUID id, FarmActivity farmActivity) throws Exception {
        FarmActivity _farmActivity = this.findOne(id);
        _farmActivity.setDate(farmActivity.getDate());
        _farmActivity.setActivities(farmActivity.getActivities());
        return farmActivityRepository.save(_farmActivity);
    }

    @Override
    public void delete(UUID id) {
        farmActivityRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        farmActivityRepository.deleteAll();
    }

    @Override
    public void deleteMany(List<FarmActivity> farmActivities) {
        farmActivityRepository.deleteAll(farmActivities);
    }

    @Override
    public Page<FarmActivity> findByFarmActivityLog(
            FarmActivityLog farmActivityLog, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        FarmActivityLog _farmActivityLog = farmActivityLogService.findOne(farmActivityLog.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmActivityRepository.findByFarmActivityLog(_farmActivityLog, pageRequest);
    }

    @Override
    public Page<FarmActivity> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        Farm _farm = farmService.findOne(farm.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmActivityRepository.findByFarm(_farm, pageRequest);
    }
}
