package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmDiary;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FarmDiaryService {
    FarmDiary create(FarmDiary farmDiary);
    Page<FarmDiary> findAll(int page, int size, String sort, String sortDirection);
    FarmDiary update(UUID id, FarmDiary farmDiary);
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmDiary> farmActivities);
    Page<FarmDiary> findByFarm(Farm farm, int page, int size, String sort, String sortDirection);
}
