package processor.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import processor.application.message.MetricsMessage;
import processor.application.message.ClientPayloadMessage;
import processor.application.message.ServerPayloadMessage;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Random;

@Slf4j
@Component
public class RequestService {

    @Autowired
    private PayloadRepository payloadRepository;

    @Autowired
    private MetricsRepository metricsRepository;

    private final Random random = new Random();

    public Mono<MetricsMessage> getMetrics() {
        return metricsRepository.getMetrics().map(m -> MetricsMessage.builder()
                .total(m.getTotal())
                .average(m.getAverage())
                .build());
    }

    public Mono<ServerResponse> addPayload(ServerRequest serverRequest) {
        Mono<ClientPayloadMessage> payloadToSave = serverRequest.bodyToMono(ClientPayloadMessage.class);
        return payloadToSave.log().flatMap(o -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(translate(payloadRepository.save(ClientPayload.builder()
                        .requestId(o.getRequestId())
                        .number(BigDecimal.valueOf(o.getQuantity() * (0.9 * random.nextDouble() + 1.1)))
                        .data(o.getData()).build())), ServerPayloadMessage.class));
    }

    private Mono<ServerPayloadMessage> translate(Mono<ClientPayload> order) {
        return order.map(o -> ServerPayloadMessage.builder()
                .requestId(o.getRequestId())
                .responseNumber(o.getNumber()).build());
    }
}
