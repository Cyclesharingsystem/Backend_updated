
import com.example.CycleSharingSystemBackend.controller.PaymentController;
import com.example.CycleSharingSystemBackend.dto.PaymentRequestDto;
import com.example.CycleSharingSystemBackend.dto.CreatePaymentResponse;
import com.example.CycleSharingSystemBackend.service.StripePaymentService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class PaymentControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private StripePaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    public void createPaymentIntent_success() throws Exception {
        PaymentRequestDto paymentRequest = new PaymentRequestDto();
        CreatePaymentResponse paymentResponse = new CreatePaymentResponse();

        // Set appropriate fields for your DTOs
        // paymentRequest.setAmount(1000);
        // paymentResponse.setClientSecret("some_client_secret");

        when(paymentService.createPaymentIntent(any(PaymentRequestDto.class))).thenReturn(paymentResponse);

        mockMvc.perform(post("/api/v1/payment/create-payment-intent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(paymentResponse)));
    }
}
