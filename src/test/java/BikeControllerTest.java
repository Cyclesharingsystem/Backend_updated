
import com.example.CycleSharingSystemBackend.controller.BikeController;
import com.example.CycleSharingSystemBackend.dto.BikeDto;
import com.example.CycleSharingSystemBackend.dto.BikeRequestDTO;
import com.example.CycleSharingSystemBackend.service.BikeService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BikeControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private BikeService bikeService;

    @InjectMocks
    private BikeController bikeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bikeController).build();
    }

    @Test
    public void newBikes_success() throws Exception {
        BikeRequestDTO bikeRequestDTO = new BikeRequestDTO();
        BikeDto bikeDto = new BikeDto();

        when(bikeService.newBike(any(BikeRequestDTO.class))).thenReturn(bikeDto);

        mockMvc.perform(post("/api/v1/Bikes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bikeRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bikeDto)));
    }

    @Test
    public void getAllBikes_success() throws Exception {
        BikeDto bikeDto1 = new BikeDto();
        BikeDto bikeDto2 = new BikeDto();

        when(bikeService.getAllBikes()).thenReturn(Arrays.asList(bikeDto1, bikeDto2));

        mockMvc.perform(get("/api/v1/Bikes"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(bikeDto1, bikeDto2))));
    }

    @Test
    public void getBikeById_success() throws Exception {
        BikeDto bikeDto = new BikeDto();

        when(bikeService.getBikeById(anyLong())).thenReturn(Optional.of(bikeDto));

        mockMvc.perform(get("/api/v1/Bikes/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bikeDto)));
    }

    @Test
    public void getBikeById_notFound() throws Exception {
        when(bikeService.getBikeById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/Bikes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateBike_success() throws Exception {
        BikeDto bikeDto = new BikeDto();

        when(bikeService.updateBike(any(BikeDto.class), anyLong())).thenReturn(bikeDto);

        mockMvc.perform(put("/api/v1/Bikes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bikeDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bikeDto)));
    }

    @Test
    public void deleteBike_success() throws Exception {
        when(bikeService.deleteBike(anyLong())).thenReturn("Bike deleted successfully");

        mockMvc.perform(delete("/api/v1/Bikes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Bike deleted successfully"));
    }

    @Test
    public void getLatestBikeId_success() throws Exception {
        when(bikeService.getLatestBikeId()).thenReturn(1L);

        mockMvc.perform(get("/api/v1/Bikes/latestBikeId"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void getTotalBikes_success() throws Exception {
        when(bikeService.getTotalBikes()).thenReturn(10);

        mockMvc.perform(get("/api/v1/Bikes/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    public void getLatestBikeDetails_success() throws Exception {
        BikeDto bikeDto = new BikeDto();

        when(bikeService.getLatestBikeDetails()).thenReturn(bikeDto);

        mockMvc.perform(get("/api/v1/Bikes/latestBikeDetails"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bikeDto)));
    }
}
