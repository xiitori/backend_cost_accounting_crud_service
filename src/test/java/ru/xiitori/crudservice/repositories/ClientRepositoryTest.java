package ru.xiitori.crudservice.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.xiitori.crudservice.models.Client;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client(
                "test_username",
                "test_name",
                "test_surname",
                "test_lastname",
                LocalDate.of(1990, 1, 1),
                "test_email",
                "test_phone",
                "test_password"
        );
        client.setRole("test_role");
    }


    @Test
    void findByUsernameTest_whenInDatabase_thenSuccess() {
        clientRepository.save(client);

        final Optional<Client> actual = clientRepository.findByUsername("test_username");

        assertNotNull(actual.orElse(null));
        assertEquals(client, actual.get());
    }

    @Test
    void findByUsername_whenNotInDatabase_thenFailure() {
        final Optional<Client> actual = clientRepository.findByUsername("test_username");

        assertNull(actual.orElse(null));
    }

    @Test
    void findByEmail_whenInDatabase_thenSuccess() {
        clientRepository.save(client);

        final Optional<Client> actual = clientRepository.findByEmail("test_email");

        assertNotNull(actual.orElse(null));
        assertEquals(client, actual.get());
    }

    @Test
    void findByEmail_whenNotInDatabase_thenFailure() {
        final Optional<Client> actual = clientRepository.findByEmail("test_email");

        assertNull(actual.orElse(null));
    }

    @Test
    void existsByEmail_whenInDatabase_thenSuccess() {
        clientRepository.save(client);

        final boolean existsByEmail = clientRepository.existsByEmail("test_email");

        assertTrue(existsByEmail);
    }

    @Test
    void existsByEmail_whenNotInDatabase_thenFailure() {
        boolean existsByEmail = clientRepository.existsByEmail("test_email");

        assertFalse(existsByEmail);
    }

    @Test
    void existsByUsername_whenInDatabase_thenSuccess() {
        clientRepository.save(client);

        final boolean existsByUsername = clientRepository.existsByUsername("test_username");

        assertTrue(existsByUsername);
    }

    @Test
    void existsByUsername_whenNotInDatabase_thenFailure() {
        boolean existsByUsername = clientRepository.existsByUsername("test_username");

        assertFalse(existsByUsername);
    }

    @Test
    void deleteByUsername_whenInDatabase_thenSuccess() {
        clientRepository.save(client);

        clientRepository.deleteByUsername("test_username");

        boolean existsByUsername = clientRepository.existsByUsername("test_username");

        assertFalse(existsByUsername);
    }
}