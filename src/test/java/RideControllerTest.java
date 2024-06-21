

import com.example.CycleSharingSystemBackend.controller.RideController;
import com.example.CycleSharingSystemBackend.dto.EndRideRequestDto;
import com.example.CycleSharingSystemBackend.dto.RideDto;
import com.example.CycleSharingSystemBackend.dto.StartRideRequestDto;
import com.example.CycleSharingSystemBackend.dto.UpdateRidePathDto;
import com.example.CycleSharingSystemBackend.model.Ride;
import com.example.CycleSharingSystemBackend.model.RidePath;
import com.example.CycleSharingSystemBackend.service.RideService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RideControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private RideService rideService;

    @InjectMocks
    private RideController rideController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(rideController).build();
    }

    @Test
    public void addRideHistory_success() throws Exception {
        Ride ride = new Ride();
        when(rideService.saveRideHistory(any(Ride.class))).thenReturn(ride);

        mockMvc.perform(post("/api/v1/ride/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ride)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(ride)));
    }

    @Test
    public void startRide_success() throws Exception {
        StartRideRequestDto startRideRequestDto = new StartRideRequestDto();
        RideDto rideDto = new RideDto();
        when(rideService.startRide(any(StartRideRequestDto.class))).thenReturn(rideDto);

        mockMvc.perform(post("/api/v1/ride/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(startRideRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(rideDto)));
    }

    @Test
    public void endRide_success() throws Exception {
        EndRideRequestDto endRideRequestDto = new EndRideRequestDto();
        RideDto rideDto = new RideDto();
        when(rideService.endRide(any(EndRideRequestDto.class))).thenReturn(rideDto);

        mockMvc.perform(post("/api/v1/ride/end")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endRideRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(rideDto)));
    }

    @Test
    public void updateRideLocation_success() throws Exception {
        UpdateRidePathDto updateRidePathDto = new UpdateRidePathDto();
        RideDto rideDto = new RideDto();
        when(rideService.updateRidePath(any(UpdateRidePathDto.class))).thenReturn(rideDto);

        mockMvc.perform(post("/api/v1/ride/update-location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRidePathDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(rideDto)));
    }

    @Test
    public void getRidePath_success() throws Exception {
        RidePath ridePath = new RidePath();
        List<RidePath> ridePaths = Arrays.asList(ridePath);
        when(rideService.getRidePath(anyLong())).thenReturn(ridePaths);

        mockMvc.perform(get("/api/v1/ride/1/path"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(ridePaths)));
    }

    @Test
    public void getRideHistoryForUser_success() throws Exception {
        RideDto rideDto = new RideDto();
        List<RideDto> rideHistory = Arrays.asList(rideDto);
        when(rideService.getRideHistoryForUser(anyLong())).thenReturn(rideHistory);

        mockMvc.perform(get("/api/v1/ride/history/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(rideHistory)));
    }

    @Test
    public void getRideHistoryForUser_noContent() throws Exception {
        when(rideService.getRideHistoryForUser(anyLong())).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/v1/ride/history/1"))
                .andExpect(status().isNoContent());
    }
}
