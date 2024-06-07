package ru.xiitori.crudservice.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xiitori.crudservice.dto.auth.LoginDTO;
import ru.xiitori.crudservice.dto.client.ClientDTO;
import ru.xiitori.crudservice.models.Client;
import ru.xiitori.crudservice.service.ClientService;
import ru.xiitori.crudservice.utils.ErrorUtils;
import ru.xiitori.crudservice.utils.ExceptionResponse;
import ru.xiitori.crudservice.utils.exceptions.RegistrationException;
import ru.xiitori.crudservice.utils.jwt.JWTUtils;
import ru.xiitori.crudservice.validation.ClientDTOValidator;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ClientService clientService;

    private final ModelMapper mapper;

    private final ClientDTOValidator clientDTOValidator;

    private final JWTUtils jwtUtils;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    public AuthController(ClientService clientService, ModelMapper mapper, ClientDTOValidator clientDTOValidator, JWTUtils jwtUtils, DaoAuthenticationProvider daoAuthenticationProvider) {
        this.clientService = clientService;
        this.mapper = mapper;
        this.clientDTOValidator = clientDTOValidator;
        this.jwtUtils = jwtUtils;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
                        loginDTO.getPassword());

        try {
            Authentication authentication = daoAuthenticationProvider.authenticate(authInputToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtils.createToken(loginDTO.getUsername());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody @Valid ClientDTO clientDTO, BindingResult bindingResult) {
        clientDTOValidator.validate(clientDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new RegistrationException(ErrorUtils.createMessage(bindingResult));
        }

        Client client = convertClientDTO(clientDTO);
        clientService.saveClient(client);
        String token = jwtUtils.createToken(client.getUsername());

        return Map.of("jwt-token", token);
    }

    public Client convertClientDTO(ClientDTO clientDTO) {
        return mapper.map(clientDTO, Client.class);
    }

    @ExceptionHandler(value = RegistrationException.class)
    public ResponseEntity<ExceptionResponse> handleException(RegistrationException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
