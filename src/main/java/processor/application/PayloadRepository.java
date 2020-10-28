package processor.application;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PayloadRepository extends ReactiveCrudRepository<ClientPayload, Long> {

    @Query("SELECT * FROM client_payload")
    Flux<ClientPayload> findAll();

}
