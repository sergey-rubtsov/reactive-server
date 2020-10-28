package processor.configuration;

import org.hamcrest.number.IsCloseTo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import processor.application.MetricsRepository;
import processor.application.ClientPayload;
import processor.application.PayloadRepository;
import processor.application.message.MetricsMessage;
import processor.application.message.ClientPayloadMessage;

import java.util.UUID;

import static java.math.BigDecimal.valueOf;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PayloadRepository payloadRepository;

    @Autowired
    private MetricsRepository metricsRepository;

    @Test
    public void addOrderRoute() {
        String uuid = UUID.randomUUID().toString();
        webTestClient
                .post().uri("/add")
                .bodyValue(ClientPayloadMessage.builder()
                        .requestId(uuid)
                        .quantity(1L)
                        .data("AAAA")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.requestId")
                .isEqualTo(uuid)
                .jsonPath("$.responseNumber")
                .value(IsCloseTo.closeTo(1.5, 0.5));
    }

    @Test
    public void getMetricsRoute() {
        payloadRepository.deleteAll().block();
        payloadRepository.save(ClientPayload.builder()
                .requestId(UUID.randomUUID().toString())
                .number(valueOf(42))
                .data("AAAA")
                .build()).block();
        webTestClient.get().uri("metrics").exchange()
                .expectBody(MetricsMessage.class).isEqualTo(MetricsMessage.builder()
                .total(1L)
                .average(valueOf(42)).build());
        payloadRepository.save(ClientPayload.builder()
                .requestId(UUID.randomUUID().toString())
                .number(valueOf(42))
                .data("BBBB")
                .build()).block();
        webTestClient.get().uri("metrics").exchange()
                .expectBody(MetricsMessage.class).isEqualTo(MetricsMessage.builder()
                .total(2L)
                .average(valueOf(42)).build());
    }
}
