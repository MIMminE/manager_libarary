package nuts.lib.manager.broker_manager.activemq;

import lombok.extern.slf4j.Slf4j;
import nuts.lib.manager.broker_manager.BrokerProducer;
import org.springframework.jms.core.JmsTemplate;


@Slf4j
public abstract class AbstractActiveMqProducer implements BrokerProducer<Object> {

    private final JmsTemplate jmsTemplate;
    private final String destination;

    public AbstractActiveMqProducer(JmsTemplate jmsTemplate, String destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }

    @Override
    public void send(Object message) {
        jmsTemplate.convertAndSend(destination, message);
        debugLogPrint(message);
    }

    protected void debugLogPrint(Object message) {
        log.debug("activemq_producer send message : {}", message);
    }
}
