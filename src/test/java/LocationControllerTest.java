
import com.example.CycleSharingSystemBackend.controller.LocationController;
import com.example.CycleSharingSystemBackend.dto.LocationDto;
import com.example.CycleSharingSystemBackend.dto.LocationRequestDto;
import com.example.CycleSharingSystemBackend.service.LocationService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LocationControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private LocationService locationService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LocationController locationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
    }

    @Test
    public void addLocation_success() throws Exception {
        LocationRequestDto locationRequestDto = new LocationRequestDto();
        LocationDto locationDto = new LocationDto();

        when(locationService.addLocation(any(LocationRequestDto.class))).thenReturn(locationDto);

        mockMvc.perform(post("/api/v1/location/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(locationDto)));
    }

    @Test
    public void getLocation_success() throws Exception {
        LocationDto locationDto = new LocationDto();

        when(locationService.getLocation(anyDouble(), anyDouble())).thenReturn(locationDto);

        mockMvc.perform(get("/api/v1/location/10.0/20.0"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(locationDto)));
    }

    @Test
    public void getLocationById_success() throws Exception {
        LocationDto locationDto = new LocationDto();

        when(locationService.findByLocationId(anyString())).thenReturn(locationDto);

        mockMvc.perform(get("/api/v1/location/locationId123"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(locationDto)));
    }

    @Test
    public void updateLocation_success() throws Exception {
        LocationDto locationDto = new LocationDto();

        when(locationService.updateLocation(any(LocationDto.class))).thenReturn(locationDto);

        mockMvc.perform(put("/api/v1/location/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(locationDto)));
    }

    @Test
    public void updateLocation_notFound() throws Exception {
        LocationDto locationDto = new LocationDto();

        when(locationService.updateLocation(any(LocationDto.class))).thenReturn(null);

        mockMvc.perform(put("/api/v1/location/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDto)))
                .andExpect(status().isNotFound());
    }
}
