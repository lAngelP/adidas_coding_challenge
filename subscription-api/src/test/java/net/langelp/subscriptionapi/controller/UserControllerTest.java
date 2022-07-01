package net.langelp.subscriptionapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.langelp.subscriptionapi.controller.advice.ExceptionAdvice;
import net.langelp.subscriptionapi.controller.dto.CreateUserRequestDto;
import net.langelp.subscriptionapi.fixtures.UserDtoFixtures;
import net.langelp.subscriptionapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new ExceptionAdvice())
                .build();
    }

    @Test
    public void tryAddNewUser() throws Exception {
        var email = "a@a.es";
        var createUserRequestDto = new CreateUserRequestDto(new Date(), null, null, true);
        when(userService.addUser(email, createUserRequestDto))
                .thenReturn(UserDtoFixtures.someValidDto(email));

        mockMvc.perform(put("/user/" + email)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    public void tryAddInvalidUserAsBirthDateIsNull() throws Exception {
        var email = "a@a.es";
        var createUserRequestDto = new CreateUserRequestDto(null, null, null, true);
        when(userService.addUser(email, createUserRequestDto))
                .thenReturn(UserDtoFixtures.someValidDto(email));

        mockMvc.perform(put("/user/" + email)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequestDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void tryAddInvalidUserAsRGPDIsNull() throws Exception {
        var email = "a@a.es";
        var createUserRequestDto = new CreateUserRequestDto(new Date(), null, null, null);
        when(userService.addUser(email, createUserRequestDto))
                .thenReturn(UserDtoFixtures.someValidDto(email));

        mockMvc.perform(put("/user/" + email)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequestDto)))
                .andExpect(status().isBadRequest());
    }


}
