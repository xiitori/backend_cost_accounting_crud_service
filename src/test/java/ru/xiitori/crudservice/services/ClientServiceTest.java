package ru.xiitori.crudservice.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.xiitori.crudservice.models.Client;
import ru.xiitori.crudservice.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    private static final String USERNAME = "username";

    private static final String EMAIL = "email";

    private static final int ID = 1;

    @Mock
    ClientRepository clientRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    ClientService clientService;

    @Test
    void getClients_shouldCallRepository() {
        final Client client = mock(Client.class);
        when(clientRepository.findAll()).thenReturn(List.of(client));

        List<Client> clients = clientService.getClients();

        assertNotNull(clients);
        assertEquals(1, clients.size());
        assertEquals(client, clients.getFirst());
    }

    @Test
    void updateClient_shouldCallRepository() {
        final Client client = mock(Client.class);
        clientService.updateClient(ID, client);

        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void saveClient_shouldCallRepository() {
        final Client client = mock(Client.class);

        clientService.saveClient(client);

        verify(clientRepository).save(client);
    }

    @Test
    void getClientById_shouldCallRepository() {
        final Client client = mock(Client.class);
        when(clientRepository.findById(ID)).thenReturn(Optional.of(client));

        Optional<Client> actual = clientService.getClientById(ID);

        assertTrue(actual.isPresent());
        assertEquals(actual.get(), client);
    }

    @Test
    void getClientByUsername_shouldCallRepository() {
        final Client client = mock(Client.class);
        when(clientService.getClientByUsername(USERNAME)).thenReturn(Optional.of(client));

        Optional<Client> actual = clientService.getClientByUsername(USERNAME);

        assertTrue(actual.isPresent());
        assertEquals(actual.get(), client);
    }

    @Test
    void getClientByEmail_shouldCallRepository() {
        final Client client = mock(Client.class);
        when(clientService.getClientByEmail(EMAIL)).thenReturn(Optional.of(client));

        Optional<Client> actual = clientService.getClientByEmail(EMAIL);

        assertTrue(actual.isPresent());
        assertEquals(actual.get(), client);
    }

    @Test
    void existsClientByEmail_shouldCallRepository() {
        clientService.existsClientByEmail(EMAIL);

        verify(clientRepository).existsByEmail(EMAIL);
    }

    @Test
    void existsClientByUsername_shouldCallRepository() {
        clientService.existsClientByUsername(USERNAME);

        verify(clientRepository).existsByUsername(USERNAME);
    }

    @Test
    void deleteClientById_shouldCallRepository() {
        clientService.deleteClient(ID);

        verify(clientRepository).deleteById(ID);
    }

    @Test
    void deleteClientByUsername_shouldCallRepository() {
        clientService.deleteClient(USERNAME);

        verify(clientRepository).deleteByUsername(USERNAME);
    }
}