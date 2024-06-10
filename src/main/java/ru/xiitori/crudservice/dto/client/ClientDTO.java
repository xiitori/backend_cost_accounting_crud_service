package ru.xiitori.crudservice.dto.client;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class ClientDTO {

    @Size(min = 4, max = 20, message = "Username may be 4-20 characters long")
    @NotNull(message = "Username can not be null")
    private String username;

    @Size(max = 100)
    @NotNull(message = "Name can not be null")
    private String name;

    @Size(max = 100)
    @NotNull(message = "Surname can not be null")
    private String surname;

    @Size(max = 100)
    @NotNull(message = "Lastname can not be null")
    private String lastname;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull(message = "Date of birth can not be null")
    private LocalDate dateOfBirth;

    @Email(message = "This is incorrect email")
    @NotNull(message = "Email can not be null")
    private String email;

    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "This is incorrect phone number")
    @NotNull(message = "Phone number can not be null")
    private String phoneNumber;

    @Size(min = 6, max = 30, message = "Password may be 6-30 characters long")
    @NotNull(message = "Password can not be null")
    private String password;

    public ClientDTO(String username, String name, String surname, String lastname, String email, String phoneNumber, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
