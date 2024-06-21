

import com.example.CycleSharingSystemBackend.controller.StationController;
import com.example.CycleSharingSystemBackend.dto.StationDistanceDTO;
import com.example.CycleSharingSystemBackend.dto.StationDto;
import com.example.CycleSharingSystemBackend.dto.StationRequestDto;
import com.example.CycleSharingSystemBackend.service.LocationService;
import com.example.CycleSharingSystemBackend.service.StationService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StationControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private StationService stationService;

    @Mock
    private LocationService locationService;

    @InjectMocks
    private StationController stationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(stationController).build();
    }

    @Test
    public void newStation_success() throws Exception {
        StationRequestDto stationRequestDto = new StationRequestDto();
        StationDto stationDto = new StationDto();

        when(stationService.addStation(any(StationRequestDto.class))).thenReturn(stationDto);

        mockMvc.perform(post("/api/v1/station/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stationRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(stationDto)));
    }

    @Test
    public void getAllStations_success() throws Exception {
        StationDto stationDto1 = new StationDto();
        StationDto stationDto2 = new StationDto();
        List<StationDto> stationList = Arrays.asList(stationDto1, stationDto2);

        when(stationService.getAllStations()).thenReturn(stationList);

        mockMvc.perform(get("/api/v1/station/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(stationList)));
    }

    @Test
    public void findNearestStations_success() throws Exception {
        StationDistanceDTO stationDistanceDTO1 = new StationDistanceDTO();
        StationDistanceDTO stationDistanceDTO2 = new StationDistanceDTO();
        List<StationDistanceDTO> stationDistanceDTOList = Arrays.asList(stationDistanceDTO1, stationDistanceDTO2);

        when(stationService.findNearestStations(anyDouble(), anyDouble(), anyInt())).thenReturn(stationDistanceDTOList);

        mockMvc.perform(get("/api/v1/station/nearest")
                        .param("latitude", "10.0")
                        .param("longitude", "20.0")
                        .param("radius", "5"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(stationDistanceDTOList)));
    }

    @Test
    public void getAvailableBikeCount_success() throws Exception {
        when(stationService.getAvailableBikeCount(anyString())).thenReturn(5);

        mockMvc.perform(get("/api/v1/station/stationId123/available-bikes"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    public void getAvailableParkingSlots_success() throws Exception {
        when(stationService.getAvailableParkingSlots(anyString())).thenReturn(10);

        mockMvc.perform(get("/api/v1/station/stationId123/available-parking-slots"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    public void getTotalAvailableBikes_success() throws Exception {
        when(stationService.getTotalAvailableBikes()).thenReturn(20);

        mockMvc.perform(get("/api/v1/station/total-available-bikes"))
                .andExpect(status().isOk())
                .andExpect(content().string("20"));
    }
}
