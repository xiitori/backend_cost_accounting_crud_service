package ru.xiitori.crudservice.controllers;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.xiitori.crudservice.config.JWTFilter;
import ru.xiitori.crudservice.models.Client;
import ru.xiitori.crudservice.models.Expense;
import ru.xiitori.crudservice.models.Income;
import ru.xiitori.crudservice.services.ClientService;
import ru.xiitori.crudservice.services.ExpenseService;
import ru.xiitori.crudservice.services.IncomeService;
import ru.xiitori.crudservice.utils.exceptions.ClientNotFoundException;
import ru.xiitori.crudservice.validation.RegistrationDTOValidator;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClientControllerTest {

    private static final int ID = 1;

    private static final String USERNAME = "username";

    @MockBean
    private ClientService clientService;

    @MockBean
    private IncomeService incomeService;

    @MockBean
    private ExpenseService expenseService;

    @SpyBean
    private ModelMapper modelMapper;

    @MockBean
    private RegistrationDTOValidator registrationDTOValidator;

    @MockBean
    private JWTFilter jwtFilter;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getClients_shouldReturnList() throws Exception {
        Client firstClient = new Client();
        Client secondClient = new Client();
        firstClient.setUsername("test_username1");
        secondClient.setUsername("test_username2");
        when(clientService.getClients()).thenReturn(List.of(firstClient, secondClient));

        mockMvc.perform(
                        get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(firstClient.getUsername()))
                .andExpect(jsonPath("$[1].username").value(secondClient.getUsername()));

        verify(clientService, times(1)).getClients();
    }

    @Test
    void clientNotFound_shouldReturnException() throws Exception {
        when(clientService.getClientById(ID)).thenThrow(new ClientNotFoundException("Client with id " + ID + " not found"));

        mockMvc.perform(get("/clients/{id}", ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(
                        ClientNotFoundException.class.getSimpleName() + ": Client with id " + ID + " not found"));
    }

    @Test
    void getClient_shouldReturnClient() throws Exception {
        Client client = new Client();
        client.setUsername("test_username");
        client.setName("test_name");
        client.setSurname("test_surname");
        client.setLastname("test_lastname");
        client.setEmail("test_email");
        client.setPhoneNumber("test_phoneNumber");
        when(clientService.getClientById(ID)).thenReturn(Optional.of(client));

        mockMvc.perform(get("/clients/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test_username"))
                .andExpect(jsonPath("$.name").value("test_name"))
                .andExpect(jsonPath("$.surname").value("test_surname"))
                .andExpect(jsonPath("$.lastname").value("test_lastname"))
                .andExpect(jsonPath("$.email").value("test_email"))
                .andExpect(jsonPath("$.phoneNumber").value("test_phoneNumber"));

        verify(clientService, times(1)).getClientById(ID);
    }

    @Test
    void getExpenses_shouldReturnList() throws Exception {
        Expense firstExpense = new Expense();
        Expense secondExpense = new Expense();
        firstExpense.setId(1);
        secondExpense.setId(2);
        firstExpense.setDescription("first expense");
        secondExpense.setDescription("second expense");
        when(expenseService.getExpensesByClientId(ID)).thenReturn(List.of(firstExpense, secondExpense));

        mockMvc.perform(
                        get("/clients/{id}/expenses", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(firstExpense.getId()))
                .andExpect(jsonPath("$[0].description").value(firstExpense.getDescription()))
                .andExpect(jsonPath("$[1].id").value(secondExpense.getId()))
                .andExpect(jsonPath("$[1].description").value(secondExpense.getDescription()));

        verify(expenseService, times(1)).getExpensesByClientId(ID);
    }

    @Test
    void getIncomes_shouldReturnList() throws Exception {
        Income firstIncome = new Income();
        Income secondIncome = new Income();
        firstIncome.setId(1);
        firstIncome.setDescription("first income");
        secondIncome.setId(2);
        secondIncome.setDescription("second income");
        when(incomeService.getIncomesByClientId(ID)).thenReturn(List.of(firstIncome, secondIncome));

        mockMvc.perform(
                        get("/clients/{id}/incomes", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(firstIncome.getId()))
                .andExpect(jsonPath("$[0].description").value(firstIncome.getDescription()))
                .andExpect(jsonPath("$[1].id").value(secondIncome.getId()))
                .andExpect(jsonPath("$[1].description").value(secondIncome.getDescription()));

        verify(incomeService, times(1)).getIncomesByClientId(ID);
    }

    @Test
    void deleteClientByUsername() throws Exception {
        mockMvc.perform(delete("/clients/username/{username}", USERNAME))
                .andExpect(status().isOk());

        verify(clientService, times(1)).deleteClient(USERNAME);
    }

    @Test
    void deleteClientById() throws Exception {
        mockMvc.perform(delete("/clients/{id}", ID))
                .andExpect(status().isOk());

        verify(clientService, times(1)).deleteClient(ID);
    }
}