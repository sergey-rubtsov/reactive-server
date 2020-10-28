package processor.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import processor.application.RequestService;
import processor.application.message.MetricsMessage;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class PayloadRouter {

    @Bean
    public RouterFunction<ServerResponse> addOrderRoute(RequestService payloadHandler) {
        return route(POST("/add").and(accept(MediaType.APPLICATION_JSON)), payloadHandler::addPayload);
    }

    @Bean
    RouterFunction<ServerResponse> getMetricsRoute(RequestService payloadHandler) {
        return route(GET("/metrics"), req -> ok().body(payloadHandler.getMetrics(), MetricsMessage.class));
    }

}
