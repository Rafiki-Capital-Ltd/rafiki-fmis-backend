package ke.co.rafiki.rafikifmis.repository;

import ke.co.rafiki.rafikifmis.domain.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, UUID> {
    PhoneNumber findByNumber(String number);
}
