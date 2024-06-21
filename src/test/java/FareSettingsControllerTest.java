
import com.example.CycleSharingSystemBackend.controller.FareSettingsController;
import com.example.CycleSharingSystemBackend.dto.FareSettingsDTO;
import com.example.CycleSharingSystemBackend.model.FareSettings;
import com.example.CycleSharingSystemBackend.service.FareSettingsService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class FareSettingsControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private FareSettingsService fareSettingsService;

    @InjectMocks
    private FareSettingsController fareSettingsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(fareSettingsController).build();
    }

    @Test
    public void getHourlyRate_success() throws Exception {
        when(fareSettingsService.getHourlyRate()).thenReturn(5.0);

        mockMvc.perform(get("/api/v1/fare/hourly-rate"))
                .andExpect(status().isOk())
                .andExpect(content().string("5.0"));
    }

    @Test
    public void updateFareSettings_success() throws Exception {
        FareSettingsDTO fareSettingsDTO = new FareSettingsDTO();
        fareSettingsDTO.setHourlyRate(5.0);
        fareSettingsDTO.setDailyRate(20.0);
        fareSettingsDTO.setWeeklyRate(100.0);
        fareSettingsDTO.setMonthlyRate(300.0);

        FareSettings updatedFareSettings = new FareSettings();
        updatedFareSettings.setHourlyRate(5.0);
        updatedFareSettings.setDailyRate(20.0);
        updatedFareSettings.setWeeklyRate(100.0);
        updatedFareSettings.setMonthlyRate(300.0);

        when(fareSettingsService.updateFareSettings(any(FareSettingsDTO.class))).thenReturn(updatedFareSettings);

        mockMvc.perform(put("/api/v1/fare/settings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fareSettingsDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(fareSettingsDTO)));
    }
}
