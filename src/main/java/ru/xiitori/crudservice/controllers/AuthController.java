package ru.xiitori.crudservice.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.xiitori.crudservice.dto.client.ClientDTO;
import ru.xiitori.crudservice.models.Client;
import ru.xiitori.crudservice.service.ClientService;
import ru.xiitori.crudservice.utils.ErrorUtils;
import ru.xiitori.crudservice.utils.ExceptionResponse;
import ru.xiitori.crudservice.utils.exceptions.RegistrationException;
import ru.xiitori.crudservice.validation.ClientDTOValidator;

@Controller
@RequestMapping("/auth")
public class RegistrationController {

    private final ClientService clientService;

    private final ModelMapper mapper;

    private final ClientDTOValidator clientDTOValidator;

    @Autowired
    public RegistrationController(ClientService clientService, ModelMapper mapper, ClientDTOValidator clientDTOValidator) {
        this.clientService = clientService;
        this.mapper = mapper;
        this.clientDTOValidator = clientDTOValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<ClientDTO> register(@RequestBody @Valid ClientDTO clientDTO, BindingResult bindingResult) {
        clientDTOValidator.validate(clientDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new RegistrationException(ErrorUtils.createMessage(bindingResult));
        }

        Client client = mapper.map(clientDTO, Client.class);
        clientService.saveClient(client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(value = RegistrationException.class)
    public ResponseEntity<ExceptionResponse> handleException(RegistrationException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
