package ru.xiitori.crudservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.xiitori.crudservice.dto.client.ClientInfoDTO;
import ru.xiitori.crudservice.dto.expense.ExpenseDTO;
import ru.xiitori.crudservice.dto.income.IncomeDTO;
import ru.xiitori.crudservice.models.Client;
import ru.xiitori.crudservice.services.ClientService;
import ru.xiitori.crudservice.services.ExpenseService;
import ru.xiitori.crudservice.services.IncomeService;
import ru.xiitori.crudservice.utils.ExceptionResponse;
import ru.xiitori.crudservice.utils.exceptions.ClientNotFoundException;
import ru.xiitori.crudservice.utils.exceptions.UpdateException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    private final ExpenseService expenseService;

    private final ModelMapper mapper;

    private final IncomeService incomeService;

    @Autowired
    public ClientController(ClientService clientService, ExpenseService expenseService, ModelMapper mapper, IncomeService incomeService) {
        this.clientService = clientService;
        this.expenseService = expenseService;
        this.mapper = mapper;
        this.incomeService = incomeService;
    }

    @GetMapping("")
    public List<ClientInfoDTO> getClients() {
        return clientService.getClients().stream().map(obj -> mapper.map(obj, ClientInfoDTO.class)).toList();
    }

    @GetMapping("/{id}")
    public ClientInfoDTO getClient(@PathVariable("id") int id) {
        Optional<Client> optional = clientService.getClientById(id);

        if (optional.isEmpty()) {
            throw new ClientNotFoundException("Client with id " + id + " not found");
        }

        return mapper.map(optional.get(), ClientInfoDTO.class);
    }

    @GetMapping("/{id}/expenses")
    public List<ExpenseDTO> getExpenses(@PathVariable("id") int id) {
        return expenseService.getExpensesByClientId(id).stream()
                .map(expense -> mapper.map(expense, ExpenseDTO.class)).toList();
    }

    @GetMapping("/{id}/incomes")
    public List<IncomeDTO> getIncomes(@PathVariable("id") int id) {
        return incomeService.getIncomesByClientId(id).stream()
                .map(income -> mapper.map(income, IncomeDTO.class)).toList();
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<?> deleteClientByUsername(@PathVariable("username") String username) {
        clientService.deleteClient(username);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClientById(@PathVariable("id") int id) {
        clientService.deleteClient(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(value = ClientNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleClientNotFound(ClientNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UpdateException.class)
    public ResponseEntity<ExceptionResponse> handleUpdate(UpdateException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.BAD_REQUEST);
    }
}
