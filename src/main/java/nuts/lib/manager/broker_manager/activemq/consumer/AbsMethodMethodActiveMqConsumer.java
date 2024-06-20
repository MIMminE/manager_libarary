package nuts.lib.manager.broker_manager.activemq.consumer;

import jakarta.jms.Message;
import org.springframework.jms.core.JmsTemplate;

import java.util.function.Consumer;

public abstract class AbsMethodMethodActiveMqConsumer extends AbstractActiveMqConsumer {
    public AbsMethodMethodActiveMqConsumer(JmsTemplate jmsTemplate, String destination, String poolName) {
        super(jmsTemplate, destination, poolName);
        super.callback = getCallback();
    }

    protected abstract Consumer<Message> getCallback();
}
