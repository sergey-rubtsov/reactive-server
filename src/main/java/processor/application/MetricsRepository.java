package processor.application;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MetricsRepository extends ReactiveCrudRepository<Metrics, Long> {

    @Query("SELECT * FROM metrics limit 1")
    Mono<Metrics> getMetrics();

}
