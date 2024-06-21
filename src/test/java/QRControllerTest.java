
import com.example.CycleSharingSystemBackend.controller.QRController;
import com.example.CycleSharingSystemBackend.dto.ResultDTO;
import com.example.CycleSharingSystemBackend.model.Bikes;
import com.example.CycleSharingSystemBackend.repository.Bikerepository;
import com.example.CycleSharingSystemBackend.service.QRService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class QRControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private QRService qrService;

    @Mock
    private Bikerepository bikeRepository;

    @InjectMocks
    private QRController qrController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(qrController).build();
    }

    @Test
    public void processQR_success() throws Exception {
        Bikes bike = new Bikes();
        ResultDTO resultDTO = new ResultDTO();
        String resultJson = objectMapper.writeValueAsString(resultDTO);

        when(bikeRepository.findById(anyLong())).thenReturn(Optional.of(bike));
        when(qrService.processQR(any(Bikes.class))).thenReturn(resultDTO);

        mockMvc.perform(get("/qr-generator/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson));
    }

    @Test
    public void processQR_bikeNotFound() throws Exception {
        when(bikeRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/qr-generator/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Bike not found"));
    }

    @Test
    public void processQR_exception() throws Exception {
        when(bikeRepository.findById(anyLong())).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/qr-generator/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Exception found: Database error"));
    }
}
