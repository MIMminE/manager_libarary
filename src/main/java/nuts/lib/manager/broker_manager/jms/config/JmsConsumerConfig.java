package nuts.lib.manager.broker_manager.jms.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nuts.lib.manager.broker_manager.ConsumerConfig;
import nuts.lib.manager.broker_manager.jms.consumer.JmsConsumer;

@Getter
@AllArgsConstructor
public class JmsConsumerConfig extends ConsumerConfig<JmsConsumer> {

    String destination;
}
