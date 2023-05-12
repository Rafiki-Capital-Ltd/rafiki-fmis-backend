package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.PhoneNumber;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface PhoneNumberService {
    PhoneNumber save(PhoneNumber phoneNumber) throws Exception;
    List<PhoneNumber> save(List<PhoneNumber> phoneNumbers) throws Exception;
    PhoneNumber findOne(UUID id) throws Exception;
    Page<PhoneNumber> findAll(int page, int size, String sort, String sortDirection);
    PhoneNumber update(UUID id, PhoneNumber phoneNumber) throws Exception;
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<PhoneNumber> farmActivities);
}
