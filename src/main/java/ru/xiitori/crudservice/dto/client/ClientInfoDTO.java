package ru.xiitori.crudservice.dto.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class ClientInfoDTO {

    private String username;

    private String name;

    private String surname;

    private String lastname;

    private LocalDate dateOfBirth;

    private String email;

    private String phoneNumber;

    public ClientInfoDTO(String username, String name, String surname, String lastname, String email, String phoneNumber) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
