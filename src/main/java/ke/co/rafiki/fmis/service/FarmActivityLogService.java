package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmActivityLog;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FarmActivityLogService {
    FarmActivityLog save(FarmActivityLog farmActivityLog) throws Exception;
    Page<FarmActivityLog> findAll(int page, int size, String sort,
                                  String sortDirection) throws Exception;
    Page<FarmActivityLog> findAll(Farm farm, int page, int size,
                                  String sort, String sortDirection) throws Exception;
    FarmActivityLog findOne(UUID id) throws Exception;
    FarmActivityLog update(UUID id, FarmActivityLog farmActivityLog) throws Exception;
    void delete(UUID id);
    void deleteMany(List<FarmActivityLog> farmActivities);
    void deleteAll();
}
