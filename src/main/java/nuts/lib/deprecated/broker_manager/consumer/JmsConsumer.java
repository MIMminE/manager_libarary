package nuts.lib.deprecated.broker_manager.consumer;

import jakarta.jms.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nuts.lib.deprecated.broker_manager.BrokerManager;
import org.springframework.jms.core.JmsTemplate;

@Deprecated(since = "2024. 06. 20")
public abstract class JmsConsumer extends BrokerManager.MessageConsumer<Message> {

    protected JmsTemplate jmsTemplate;
    protected JmsConsumerConfig config;

    public JmsConsumer(JmsTemplate jmsTemplate, JmsConsumerConfig config) {
        this.jmsTemplate = jmsTemplate;
        this.config = config;
    }

    @Getter
    @AllArgsConstructor
    public static class JmsConsumerConfig extends BrokerManager.ConsumerConfig<JmsConsumer> {

        String destination;
    }
}
