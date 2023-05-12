package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.PhoneNumber;
import ke.co.rafiki.fmis.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, UUID> {
    PhoneNumber findByNumber(String number);

    List<PhoneNumber> findByUser(User user);
}
