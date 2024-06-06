package ru.xiitori.crudservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xiitori.crudservice.dto.ExpenseDTO;
import ru.xiitori.crudservice.dto.client.ClientDTO;
import ru.xiitori.crudservice.dto.client.ClientInfoDTO;
import ru.xiitori.crudservice.models.Client;
import ru.xiitori.crudservice.security.ClientDetails;
import ru.xiitori.crudservice.service.ClientService;
import ru.xiitori.crudservice.service.ExpenseService;
import ru.xiitori.crudservice.utils.exceptions.ClientNotFoundException;
import ru.xiitori.crudservice.utils.ErrorUtils;
import ru.xiitori.crudservice.utils.ExceptionResponse;
import ru.xiitori.crudservice.utils.exceptions.RegistrationException;
import ru.xiitori.crudservice.validation.ClientDTOValidator;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    private final ExpenseService expenseService;

    private final ModelMapper mapper;

    private final ClientDTOValidator clientDTOValidator;

    @Autowired
    public ClientController(ClientService clientService, ExpenseService expenseService, ModelMapper mapper, ClientDTOValidator clientDTOValidator) {
        this.clientService = clientService;
        this.expenseService = expenseService;
        this.mapper = mapper;
        this.clientDTOValidator = clientDTOValidator;
    }

    @GetMapping("/info/{id}")
    public ClientInfoDTO info(@PathVariable("id") int id) {
        Optional<Client> optional = clientService.getClient(id);

        if (optional.isEmpty()) {
            throw new ClientNotFoundException("Client with id " + id + " not found");
        }

        return mapper.map(optional.get(), ClientInfoDTO.class);
    }

    @GetMapping("/expenses")
    public List<ExpenseDTO> getExpenses() {
        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int clientId = clientDetails.getClient().getId();
        return expenseService.getAllExpensesByClientId(clientId).stream().map(expense -> mapper.map(expense, ExpenseDTO.class)).toList();
    }

    @GetMapping("/info")
    public ClientInfoDTO info() {
        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = clientDetails.getClient();

        return mapper.map(client, ClientInfoDTO.class);
    }

    @GetMapping("/all")
    public List<ClientInfoDTO> getAllClients() {
        return clientService.getAll().stream().map(obj -> mapper.map(obj, ClientInfoDTO.class)).toList();
    }

    /*
    в update нужно добавить валидацию, нельзя использовать валидацию регистрации, потому что запросом можно и не менять
    username и email. либо добавлять другой класс валидации, либо подключить security и получить из сессии текущего
    пользователя и сравнить с его id при валидации
     */

    @PostMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable("id") int id, @RequestBody ClientDTO clientDTO,
                                          BindingResult result) {
        clientDTOValidator.validate(clientDTO, result);

        if (result.hasErrors()) {
            throw new RegistrationException(ErrorUtils.createMessage(result));
        }

        Optional<Client> clientToUpdate = clientService.getClient(id);

        if (clientToUpdate.isEmpty()) {
            throw new ClientNotFoundException("Client with id " + id + " not found");
        }

        Client client = mapper.map(clientDTO, Client.class);
        clientService.updateClient(client, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteClient(@PathVariable("username") String username) {
        clientService.deleteClient(username);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(value = ClientNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleClientNotFound(ClientNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
