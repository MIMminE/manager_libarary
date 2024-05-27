package nuts.lib.manager.broker_manager.jms.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nuts.lib.manager.broker_manager.ProducerConfig;
import nuts.lib.manager.broker_manager.jms.producer.JmsProducer;

@Getter
@AllArgsConstructor
public class JmsProducerConfig extends ProducerConfig<JmsProducer> {

    String destination;

}