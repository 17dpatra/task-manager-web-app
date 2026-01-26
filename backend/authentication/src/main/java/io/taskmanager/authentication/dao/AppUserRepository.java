package io.taskmanager.authentication.dao;

import io.taskmanager.authentication.domain.user.Userprincipal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<Userprincipal, Long> {
    Optional<Userprincipal> findByUsername(String username);

    boolean existsByUsername(String username);
}
