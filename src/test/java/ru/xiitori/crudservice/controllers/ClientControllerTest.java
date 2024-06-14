package ru.xiitori.crudservice.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ru.xiitori.crudservice.dto.client.ClientInfoDTO;
import ru.xiitori.crudservice.models.Client;
import ru.xiitori.crudservice.services.ClientService;
import ru.xiitori.crudservice.services.ExpenseService;
import ru.xiitori.crudservice.services.IncomeService;
import ru.xiitori.crudservice.validation.ClientDTOValidator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private ClientService clientService;

    @Mock
    private IncomeService incomeService;

    @Mock
    private ExpenseService expenseService;

    @Mock
    private ClientDTOValidator clientDTOValidator;

    @InjectMocks
    private ClientController clientController;

    @Test
    void getClients() {
        Client client = mock(Client.class);
        when(clientService.getClients()).thenReturn(List.of(client));

        List<ClientInfoDTO> clients = clientController.getClients();
        assertNotNull(clients);
        assertFalse(clients.isEmpty());
        assertEquals(clients.getFirst(), modelMapper.map(client, ClientInfoDTO.class));
    }

    @Test
    void getClient() {
    }

    @Test
    void getExpenses() {
    }

    @Test
    void getIncomes() {
    }

    @Test
    void updateClient() {
    }

    @Test
    void deleteClientByUsername() {
    }

    @Test
    void deleteClientById() {
    }
}