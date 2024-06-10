package ru.xiitori.crudservice.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.xiitori.crudservice.service.ExpenseService;
import ru.xiitori.crudservice.service.IncomeService;

import java.time.ZonedDateTime;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Component
@ExtendWith(MockitoExtension.class)
public class ProfileControllerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ExpenseService expenseService;

    @Mock
    private IncomeService incomeService;

    private String secret = "kefvnaobkhnleavaerhklbearhikaregbwijk";

    private String token;

    @InjectMocks
    private ProfileController profileController;

    private MockMvc mockMvc;

    @BeforeEach
    public void initToken() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();
        token = JWT.create()
                .withSubject("Client details")
                .withIssuer("xiitori")
                .withIssuedAt(new Date())
                .withExpiresAt(ZonedDateTime.now().plusDays(1).toInstant())
                .withClaim("username", "xiitori")
                .sign(Algorithm.HMAC256(secret));
    }

    @Test
    void getClientInfoTest() throws Exception {
        mockMvc.perform(get("/profile")
                        .header("Authorization", "Bearer " + token))
                .andExpectAll(
                        jsonPath("username").value("xiitori"),
                        jsonPath("name").value("Sergey"),
                        jsonPath("surname").value("Darvin"),
                        jsonPath("lastname").value("Igorevich"),
                        jsonPath("dateOfBirth").value("2004-07-30"),
                        jsonPath("email").value("srg.darvin@gmail.com"),
                        jsonPath("phoneNumber").value("+79964515300")
                );
    }
}
