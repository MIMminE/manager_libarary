package nuts.lib.manager.broker_manager.jms;

import jakarta.jms.Message;
import org.springframework.jms.core.JmsTemplate;

import java.util.function.Consumer;

public class JmsConsumer {
    private final JmsTemplate jmsTemplate;
    private final String destination;

    public JmsConsumer(JmsTemplate jmsTemplate, String destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }

    public void receive(Consumer<Message> callback) {
        new Thread(() -> {
            synchronized (this) {
                while (true) {
                    Message receive = jmsTemplate.receive(destination);
                    callback.accept(receive);
                }
            }
        }
        ).start();

    }
}


