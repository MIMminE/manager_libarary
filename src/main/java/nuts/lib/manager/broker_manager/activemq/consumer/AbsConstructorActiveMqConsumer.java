package nuts.lib.manager.broker_manager.activemq.consumer;

import jakarta.jms.Message;
import org.springframework.jms.core.JmsTemplate;

import java.util.function.Consumer;

public abstract class AbsConstructorActiveMqConsumer extends AbstractActiveMqConsumer{
    public AbsConstructorActiveMqConsumer(JmsTemplate jmsTemplate, String destination, Consumer<Message> callback, String poolName) {
        super(jmsTemplate, destination, poolName);
        super.callback = callback;
    }

    public AbsConstructorActiveMqConsumer(JmsTemplate jmsTemplate, String destination, Consumer<Message> callback, String poolName, int nThread) {
        super(jmsTemplate, destination, poolName, nThread);
        super.callback = callback;
    }
}
