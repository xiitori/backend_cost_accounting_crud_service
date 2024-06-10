package ru.xiitori.crudservice.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.xiitori.crudservice.dto.client.ClientDTO;
import ru.xiitori.crudservice.security.ClientDetails;
import ru.xiitori.crudservice.service.ClientService;

import java.time.LocalDate;

@Component
public class ClientDTOUpdateValidator implements Validator {

    private final ClientService clientService;

    @Autowired
    public ClientDTOUpdateValidator(ClientService clientService) {
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

        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int clientId = clientDetails.getClient().getId();

        if (clientService.existsClientByUsername(username) &&
                clientService.getClientByUsername(username).get().getId() != clientId) {
            errors.rejectValue("username", null, "Username already exists");
        }

        if (clientService.existsClientByEmail(email) &&
                clientService.getClientByEmail(email).get().getId() != clientId) {
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
        return localDate.isBefore(LocalDate.of(1920, 1, 1)) || localDate.isAfter(LocalDate.now());
    }
}
