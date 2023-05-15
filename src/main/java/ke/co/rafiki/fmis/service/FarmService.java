package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.*;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface FarmService {
    Farm save(Farm farm) throws Exception;
    List<Farm> findAll(int page, int size, String sort, String sortDirection);
    Farm findOne(UUID id) throws Exception;
    Farm update(UUID id, Farm farm) throws Exception;
    void delete(UUID id);
    void deleteMany(List<Farm> farms);
    void deleteAll();
    Page<Farm> findByFarmer(
            Principal farmer, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
}
