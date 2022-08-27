package smswh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smswh.account.config.AdminUser;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long>{
    Optional<AdminUser> findByAdminId(String adminId);
}
