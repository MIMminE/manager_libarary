package nuts.lib.manager.broker_manager.jms.producer;

import nuts.lib.manager.broker_manager.MessageProducer;
import nuts.lib.manager.broker_manager.jms.config.JmsProducerConfig;
import org.springframework.jms.core.JmsTemplate;

public class JmsProducer implements MessageProducer{

    private final JmsTemplate jmsTemplate;
    private final JmsProducerConfig config;


    public JmsProducer(JmsTemplate jmsTemplate, JmsProducerConfig config) {
        this.jmsTemplate = jmsTemplate;
        this.config = config;
    }


    public void send(Object message) {
        jmsTemplate.convertAndSend(config.getDestination(), message);

    }

    @Override
    public void send() {

    }
}
