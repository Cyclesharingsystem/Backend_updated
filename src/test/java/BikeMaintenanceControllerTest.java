

import com.example.CycleSharingSystemBackend.controller.BikeMaintenanceController;
import com.example.CycleSharingSystemBackend.dto.BikeMaintenanceDto;
import com.example.CycleSharingSystemBackend.dto.BikeMaintenanceRequestDto;
import com.example.CycleSharingSystemBackend.model.BikeMaintenance;
import com.example.CycleSharingSystemBackend.service.BikeMaintenanceService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BikeMaintenanceControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private BikeMaintenanceService bikeMaintenanceService;

    @InjectMocks
    private BikeMaintenanceController bikeMaintenanceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bikeMaintenanceController).build();
    }

    @Test
    public void getAllBikeMaintenances_success() throws Exception {
        BikeMaintenanceDto bikeMaintenanceDto1 = new BikeMaintenanceDto();
        BikeMaintenanceDto bikeMaintenanceDto2 = new BikeMaintenanceDto();
        List<BikeMaintenanceDto> bikeMaintenanceDtos = Arrays.asList(bikeMaintenanceDto1, bikeMaintenanceDto2);

        when(bikeMaintenanceService.getAllBikeMaintenances()).thenReturn(bikeMaintenanceDtos);

        mockMvc.perform(get("/api/v1/bikes/maintenance/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bikeMaintenanceDtos)));
    }

    @Test
    public void createBikeMaintenance_success() throws Exception {
        BikeMaintenanceRequestDto bikeMaintenanceRequestDto = new BikeMaintenanceRequestDto();

        mockMvc.perform(post("/api/v1/bikes/maintenance/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bikeMaintenanceRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Bike Maintenance created successfully"));
    }

    @Test
    public void updateBikeMaintenance_success() throws Exception {
        BikeMaintenance bikeMaintenance = new BikeMaintenance();

        when(bikeMaintenanceService.updateBikeMaintenance(any(BikeMaintenance.class), anyLong())).thenReturn(true);

        mockMvc.perform(put("/api/v1/bikes/maintenance/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bikeMaintenance)))
                .andExpect(status().isOk())
                .andExpect(content().string("Bike Maintenance updated successfully"));
    }

    @Test
    public void updateBikeMaintenance_notFound() throws Exception {
        BikeMaintenance bikeMaintenance = new BikeMaintenance();

        when(bikeMaintenanceService.updateBikeMaintenance(any(BikeMaintenance.class), anyLong())).thenReturn(false);

        mockMvc.perform(put("/api/v1/bikes/maintenance/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bikeMaintenance)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Bike Maintenance not found"));
    }

    @Test
    public void deleteBikeMaintenance_success() throws Exception {
        when(bikeMaintenanceService.deleteBikeMaintenanceRecord(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/v1/bikes/maintenance/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Bike Maintenance Record deleted successfully"));
    }

    @Test
    public void deleteBikeMaintenance_notFound() throws Exception {
        when(bikeMaintenanceService.deleteBikeMaintenanceRecord(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/api/v1/bikes/maintenance/delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Bike Maintenance Record not found"));
    }

    @Test
    public void getBikesInMaintenanceCount_success() throws Exception {
        when(bikeMaintenanceService.countBikeIdsInMaintenance()).thenReturn(10L);

        mockMvc.perform(get("/api/v1/bikes/maintenance/bikes-in-maintenance-count"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }
}
