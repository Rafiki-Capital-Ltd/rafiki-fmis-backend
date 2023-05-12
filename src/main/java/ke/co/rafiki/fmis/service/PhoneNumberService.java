package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.PhoneNumber;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface PhoneNumberService {
    PhoneNumber create(PhoneNumber phoneNumber);
    List<PhoneNumber> create(List<PhoneNumber> phoneNumbers);
    Page<PhoneNumber> findAll(int page, int size, String sort, String sortDirection);
    PhoneNumber update(UUID id, PhoneNumber phoneNumber);
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<PhoneNumber> farmActivities);
}
