package ke.co.rafiki.fmis.repository;

import jakarta.transaction.Transactional;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    List<User> findByGender(String gender);

    @Query(value = "SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.name = :role")
    Integer getUserRoleCount(@Param("role") String role);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_roles ur WHERE ur.user_id = :user_id", nativeQuery = true)
    void disassociateRoleFromUser(@Param("user_id") UUID user_id);
}
