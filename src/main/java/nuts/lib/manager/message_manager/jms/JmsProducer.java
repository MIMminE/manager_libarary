package nuts.lib.manager.message_manager.jms;

import lombok.Synchronized;
import org.springframework.jms.core.JmsTemplate;

public class JmsProducer {

    private final JmsTemplate jmsTemplate;
    private final String destination;


    public JmsProducer(JmsTemplate jmsTemplate, String destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }


    public void send(Object message) {
        jmsTemplate.convertAndSend(destination, message);

    }
}
