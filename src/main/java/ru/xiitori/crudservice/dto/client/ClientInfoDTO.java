package ru.xiitori.crudservice.dto.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientInfoDTO that = (ClientInfoDTO) o;
        return Objects.equals(username, that.username) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(lastname, that.lastname) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(email, that.email) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, name, surname, lastname, dateOfBirth, email, phoneNumber);
    }
}
