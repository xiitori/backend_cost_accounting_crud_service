package ru.xiitori.crudservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.xiitori.crudservice.dto.LoginDTO;
import ru.xiitori.crudservice.dto.client.ClientDTO;
import ru.xiitori.crudservice.models.Client;
import ru.xiitori.crudservice.service.ClientService;
import ru.xiitori.crudservice.utils.jwt.JWTUtils;
import ru.xiitori.crudservice.validation.ClientDTOValidator;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private ClientService clientService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ClientDTOValidator clientDTOValidator;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private JWTUtils jwtUtils = new JWTUtils();

    @Mock
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
        Mockito.when(jwtUtils.createToken(any())).thenReturn("TOKEN");
    }

    @Test
    void registerTest() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setUsername("dwvfdjvcwkjl");
        clientDTO.setName("sdhucdjcdw");
        clientDTO.setSurname("dcdwlubcsdcljk");
        clientDTO.setLastname("cklwdckojnkdclqe");
        clientDTO.setDateOfBirth(LocalDate.of(1998, 8, 2));
        clientDTO.setEmail("sdcbe@gmail.com");
        clientDTO.setPhoneNumber("+79356785632");
        clientDTO.setPassword("test_password");

        Mockito.when(modelMapper.map(any(), eq(Client.class))).thenReturn(new ModelMapper().map(clientDTO, Client.class));

        String jsonClient = objectMapper.writeValueAsString(clientDTO);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonClient))
                .andExpect(status().isOk())
                .andExpect(jsonPath("jwt-token").value("TOKEN"));
    }

    @Test
    void loginTest() throws Exception {
        LoginDTO loginDTO = new LoginDTO();

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("jwt-token").value("TOKEN"));
    }
}
