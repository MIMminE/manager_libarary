package nuts.lib.deprecated.broker_manager.producer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nuts.lib.deprecated.broker_manager.BrokerManager;
import org.springframework.jms.core.JmsTemplate;

@Deprecated(since = "2024. 06. 20")
public abstract class JmsProducer extends BrokerManager.MessageProducer<Object> {

    protected JmsTemplate jmsTemplate;
    protected JmsProducerConfig config;

    public JmsProducer(JmsTemplate jmsTemplate, JmsProducerConfig config) {
        this.jmsTemplate = jmsTemplate;
        this.config = config;
    }

    @Getter
    @AllArgsConstructor
    public static class JmsProducerConfig extends BrokerManager.ProducerConfig<JmsProducer> {

        String destination;

    }
}
