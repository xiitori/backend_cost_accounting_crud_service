package ru.xiitori.crudservice.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.xiitori.crudservice.dto.client.ClientDTO;
import ru.xiitori.crudservice.services.ClientService;

import java.time.LocalDate;

@Component
public class ClientDTOValidator implements Validator {

    private final ClientService clientService;

    @Autowired
    public ClientDTOValidator(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ClientDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClientDTO clientDTO = (ClientDTO) target;

        String username = clientDTO.getUsername();
        String email = clientDTO.getEmail();
        LocalDate date = clientDTO.getDateOfBirth();

        if (clientService.existsClientByUsername(username)) {
            errors.rejectValue("username", null, "Username already exists");
        }

        if (clientService.existsClientByEmail(email)) {
            errors.rejectValue("email", null, "Email already exists");
        }

        if (date != null) {
            if (isInvalidDate(date)) {
                errors.rejectValue("dateOfBirth", null, "Invalid date");
            } else if (checkAge(date)) {
                errors.rejectValue("dateOfBirth", null, "Age is too low");
            }
        }
    }

    public boolean checkAge(LocalDate localDate) {
        return localDate.isAfter(LocalDate.now().minusYears(14));
    }

    public boolean isInvalidDate(LocalDate localDate) {
        return localDate.isBefore(LocalDate.of(1920, 1, 1))|| localDate.isAfter(LocalDate.now());
    }
}
