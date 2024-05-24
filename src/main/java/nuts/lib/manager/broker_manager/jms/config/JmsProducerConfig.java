package nuts.lib.manager.broker_manager.jms.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nuts.lib.manager.broker_manager.ProducerConfig;

@AllArgsConstructor
@Getter
public class JmsProducerConfig extends ProducerConfig {

    String destination;

}