package ke.co.rafiki.fmis.repository;

import ke.co.rafiki.fmis.domain.Gender;
import ke.co.rafiki.fmis.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    List<User> findByGender(Gender gender);
}
