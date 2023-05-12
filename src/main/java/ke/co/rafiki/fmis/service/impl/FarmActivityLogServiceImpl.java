package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmActivityLog;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmActivityLogRepository;
import ke.co.rafiki.fmis.service.FarmActivityLogService;
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
public class FarmActivityLogServiceImpl implements FarmActivityLogService {

    private final FarmActivityLogRepository farmActivityLogRepository;
    private final FarmService farmService;

    public FarmActivityLogServiceImpl(
            FarmActivityLogRepository farmActivityLogRepository, FarmService farmService
    ) {
        this.farmActivityLogRepository = farmActivityLogRepository;
        this.farmService = farmService;
    }

    @Override
    public FarmActivityLog create(FarmActivityLog farmActivityLog) throws Exception {
        Farm farm = farmService.findOne(farmActivityLog.getFarm().getId());
        farmActivityLog.setFarm(farm);
        return farmActivityLogRepository.save(farmActivityLog);
    }

    @Override
    public Page<FarmActivityLog> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmActivityLogRepository.findAll(pageRequest);
    }

    @Override
    public FarmActivityLog findOne(UUID id) throws Exception {
        return farmActivityLogRepository.findById(id).orElseThrow(() -> {
            String message = "Farm activity log id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public FarmActivityLog findOne(Farm farm) throws Exception {
        return farmActivityLogRepository.findByFarm(farm).orElseThrow(() -> {
            String message = "Farm activity log of farm " + farm + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public FarmActivityLog update(UUID id, FarmActivityLog farmActivityLog) throws Exception {
        FarmActivityLog _farmActivityLog = this.findOne(id);
        _farmActivityLog.setYear(farmActivityLog.getYear());
        _farmActivityLog.setName(farmActivityLog.getName());
        return farmActivityLogRepository.save(_farmActivityLog);
    }

    @Override
    public void delete(UUID id) {
        farmActivityLogRepository.deleteById(id);
    }

    @Override
    public void deleteMany(List<FarmActivityLog> farmActivities) {
        farmActivityLogRepository.deleteAll(farmActivities);
    }

    @Override
    public void deleteAll() {
        farmActivityLogRepository.deleteAll();
    }
}
