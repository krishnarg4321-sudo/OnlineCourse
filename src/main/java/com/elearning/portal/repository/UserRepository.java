package com.elearning.portal.repository;

import com.elearning.portal.entity.User;
import com.elearning.portal.util.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    Page<User> findByRole(Role role, Pageable pageable);
}
