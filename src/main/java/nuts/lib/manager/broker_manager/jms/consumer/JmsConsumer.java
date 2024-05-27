package nuts.lib.manager.broker_manager.jms.consumer;

import jakarta.jms.Message;
import nuts.lib.manager.broker_manager.MessageConsumer;
import nuts.lib.manager.broker_manager.jms.config.JmsConsumerConfig;
import org.springframework.jms.core.JmsTemplate;

public abstract class JmsConsumer extends MessageConsumer<Message> {

    protected JmsTemplate jmsTemplate;
    protected JmsConsumerConfig config;

    public JmsConsumer(JmsTemplate jmsTemplate, JmsConsumerConfig config) {
        this.jmsTemplate = jmsTemplate;
        this.config = config;
    }
}
