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
import ru.xiitori.crudservice.dto.auth.RegistrationDTO;
import ru.xiitori.crudservice.models.Client;
import ru.xiitori.crudservice.services.ClientService;
import ru.xiitori.crudservice.utils.ErrorUtils;
import ru.xiitori.crudservice.utils.ExceptionResponse;
import ru.xiitori.crudservice.utils.exceptions.LoginException;
import ru.xiitori.crudservice.utils.exceptions.RegistrationException;
import ru.xiitori.crudservice.utils.jwt.JWTUtils;
import ru.xiitori.crudservice.validation.RegistrationDTOValidator;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ClientService clientService;

    private final ModelMapper mapper;

    private final RegistrationDTOValidator registrationDTOValidator;

    private final JWTUtils jwtUtils;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    public AuthController(ClientService clientService, ModelMapper mapper, RegistrationDTOValidator registrationDTOValidator, JWTUtils jwtUtils, DaoAuthenticationProvider daoAuthenticationProvider) {
        this.clientService = clientService;
        this.mapper = mapper;
        this.registrationDTOValidator = registrationDTOValidator;
        this.jwtUtils = jwtUtils;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody @Valid LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new LoginException(ErrorUtils.createMessage(bindingResult));
        }

        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
                        loginDTO.getPassword());

        Authentication authentication = daoAuthenticationProvider.authenticate(authInputToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.createToken(loginDTO.getUsername());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody @Valid RegistrationDTO registrationDTO, BindingResult bindingResult) {
        registrationDTOValidator.validate(registrationDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new RegistrationException(ErrorUtils.createMessage(bindingResult));
        }

        Client client = mapper.map(registrationDTO, Client.class);
        clientService.saveClient(client);
        String token = jwtUtils.createToken(client.getUsername());

        return Map.of("jwt-token", token);
    }

    @ExceptionHandler(value = RegistrationException.class)
    public ResponseEntity<ExceptionResponse> handleException(RegistrationException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ExceptionResponse> handleException(LoginException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.UNAUTHORIZED);
    }
}
