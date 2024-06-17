package ru.xiitori.crudservice.dto.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginDTO {

    @Size(min = 4, max = 20, message = "Username may be 4-20 characters long")
    @NotNull(message = "Username can not be null")
    private String username;

    @Size(min = 6, max = 30, message = "Password may be 6-30 characters long")
    @NotNull(message = "Password can not be null")
    private String password;

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
