package nuts.lib.manager.broker_manager.jms.producer;

import nuts.lib.manager.broker_manager.MessageProducer;
import nuts.lib.manager.broker_manager.jms.config.JmsProducerConfig;
import org.springframework.jms.core.JmsTemplate;

public abstract class JmsProducer extends MessageProducer<Object> {

    protected JmsTemplate jmsTemplate;
    protected JmsProducerConfig config;

    public JmsProducer(JmsTemplate jmsTemplate, JmsProducerConfig config) {
        this.jmsTemplate = jmsTemplate;
        this.config = config;
    }
}
