package ru.xiitori.crudservice.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xiitori.crudservice.dto.client.ClientDTO;
import ru.xiitori.crudservice.dto.client.ClientInfoDTO;
import ru.xiitori.crudservice.dto.expense.ExpenseDTO;
import ru.xiitori.crudservice.dto.income.IncomeDTO;
import ru.xiitori.crudservice.models.Client;
import ru.xiitori.crudservice.service.ClientService;
import ru.xiitori.crudservice.service.ExpenseService;
import ru.xiitori.crudservice.service.IncomeService;
import ru.xiitori.crudservice.utils.ErrorUtils;
import ru.xiitori.crudservice.utils.ExceptionResponse;
import ru.xiitori.crudservice.utils.exceptions.ClientNotFoundException;
import ru.xiitori.crudservice.utils.exceptions.UpdateException;
import ru.xiitori.crudservice.validation.ClientDTOUpdateValidator;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    private final ExpenseService expenseService;

    private final ModelMapper mapper;

    private final IncomeService incomeService;

    private final ClientDTOUpdateValidator clientDTOUpdateValidator;

    @Autowired
    public ClientController(ClientService clientService, ExpenseService expenseService, ModelMapper mapper, IncomeService incomeService, ClientDTOUpdateValidator clientDTOUpdateValidator) {
        this.clientService = clientService;
        this.expenseService = expenseService;
        this.mapper = mapper;
        this.incomeService = incomeService;
        this.clientDTOUpdateValidator = clientDTOUpdateValidator;
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

    //TODO пока пойдет так, но в будущем нужно отдельное DTO под апдейт, потому что хочется уметь только одно поле редачить
    @PostMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable("id") int id, @RequestBody @Valid ClientDTO clientDTO,
                                          BindingResult result) {
        clientDTOUpdateValidator.validate(clientDTO, result);

        if (result.hasErrors()) {
            throw new UpdateException(ErrorUtils.createMessage(result));
        }

        Optional<Client> clientToUpdate = clientService.getClientById(id);

        if (clientToUpdate.isEmpty()) {
            throw new ClientNotFoundException("Client with id " + id + " not found");
        }

        Client client = mapper.map(clientDTO, Client.class);
        clientService.updateClient(client, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
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
