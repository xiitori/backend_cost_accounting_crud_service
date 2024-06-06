package ru.xiitori.crudservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xiitori.crudservice.models.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByUsername(String clientName);

    Optional<Client> findByEmail(String clientEmail);

    boolean existsByEmail(String clientEmail);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);
}
