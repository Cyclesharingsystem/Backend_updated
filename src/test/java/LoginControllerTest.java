

import com.example.CycleSharingSystemBackend.controller.LoginController;
import com.example.CycleSharingSystemBackend.model.Login;
import com.example.CycleSharingSystemBackend.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class LoginControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void login_success() throws Exception {
        Login loginRequest = new Login();
        loginRequest.setUsername("user");
        loginRequest.setPassword("password");

        Login loginResponse = new Login();
        loginResponse.setUsername("user");
        loginResponse.setPassword("password");

        when(loginService.login(anyString(), anyString())).thenReturn(loginResponse);

        mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("user Logged in Successfully"));
    }

    @Test
    public void login_invalidCredentials() throws Exception {
        Login loginRequest = new Login();
        loginRequest.setUsername("user");
        loginRequest.setPassword("wrongpassword");

        when(loginService.login(anyString(), anyString())).thenReturn(null);

        mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid user name or password"));
    }
}
