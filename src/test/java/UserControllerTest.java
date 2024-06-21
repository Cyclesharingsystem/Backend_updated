
import com.example.CycleSharingSystemBackend.controller.UserController;
import com.example.CycleSharingSystemBackend.dto.*;
import com.example.CycleSharingSystemBackend.model.User;
import com.example.CycleSharingSystemBackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void register_success() throws Exception {
        RegisterDto registerDto = new RegisterDto();
        RegisterDto responseDto = new RegisterDto();

        when(userService.register(any(RegisterDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    public void login_success() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");

        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");

        when(userService.login(anyString())).thenReturn(userDto);

        mockMvc.perform(post("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    public void verifyUser_success() throws Exception {
        UserDto userDto = new UserDto();

        when(userService.verify(anyString(), anyString())).thenReturn(userDto);

        mockMvc.perform(post("/api/v1/user/verify")
                        .param("email", "test@example.com")
                        .param("otp", "123456"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    public void verifyUserLogin_success() throws Exception {
        UserDto userDto = new UserDto();

        when(userService.verifyLogin(anyString(), anyString())).thenReturn(userDto);

        mockMvc.perform(post("/api/v1/user/verifyLogin")
                        .param("email", "test@example.com")
                        .param("otp", "123456"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    public void userLocationUpdate_success() throws Exception {
        User user = new User();
        LocationRequestDto locationRequestDto = new LocationRequestDto();

        when(userService.UserLocationUpdate(anyLong(), any(LocationRequestDto.class))).thenReturn(user);

        mockMvc.perform(put("/api/v1/user/update/location/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    public void updateUser_success() throws Exception {
        User user = new User();
        UserLocationDTO userLocationDTO = new UserLocationDTO();

        when(userService.updateUserLoc(any(UserLocationDTO.class))).thenReturn(user);

        mockMvc.perform(put("/api/v1/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLocationDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    public void getInRideUsers_success() throws Exception {
        User user = new User();
        List<User> users = Arrays.asList(user);

        when(userService.getInRideUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/user/inRide"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(users)));
    }

    @Test
    public void getCurrentOnRideUsersWithLocation_success() throws Exception {
        LiveRideUserDTO liveRideUserDTO = new LiveRideUserDTO();
        List<LiveRideUserDTO> users = Arrays.asList(liveRideUserDTO);

        when(userService.getCurrentOnRideUsersWithLocation()).thenReturn(users);

        mockMvc.perform(get("/api/v1/user/onRideUsers"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(users)));
    }

    @Test
    public void getTotalVerifiedUsers_success() throws Exception {
        when(userService.getTotalVerifiedUsers()).thenReturn(10);

        mockMvc.perform(get("/api/v1/user/totalVerifiedUsers"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    public void getTotalOnRideUsers_success() throws Exception {
        when(userService.getTotalOnRideUsers()).thenReturn(5);

        mockMvc.perform(get("/api/v1/user/totalonRideUsers"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    public void getAllUsers_success() throws Exception {
        User user = new User();
        List<User> users = Arrays.asList(user);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(users)));
    }

    @Test
    public void getInRideUsersByUserId_success() throws Exception {
        User user = new User();
        List<User> users = Arrays.asList(user);

        when(userService.getInRideUsersByUserId(anyLong())).thenReturn(users);

        mockMvc.perform(get("/api/v1/user/inRide/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(users)));
    }

    @Test
    public void getUserById_success() throws Exception {
        User user = new User();

        when(userService.getUserById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/api/v1/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    public void getUserById_notFound() throws Exception {
        when(userService.getUserById(anyLong())).thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(get("/api/v1/user/1"))
                .andExpect(status().isNotFound());
    }
}
