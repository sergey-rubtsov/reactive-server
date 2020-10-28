package processor.application.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class MetricsMessage implements Serializable {

    Long total;

    BigDecimal average;

}
