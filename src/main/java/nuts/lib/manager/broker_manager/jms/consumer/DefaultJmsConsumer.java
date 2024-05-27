package nuts.lib.manager.broker_manager.jms.consumer;

import jakarta.jms.Message;
import nuts.lib.manager.broker_manager.jms.config.JmsConsumerConfig;
import nuts.lib.manager.executor_manager.ExecutorManager;
import nuts.lib.manager.executor_manager.executor.ExecutorBuilder;
import org.springframework.jms.core.JmsTemplate;

import java.util.function.Consumer;

public class DefaultJmsConsumer extends JmsConsumer {

    private final ExecutorManager consumerExecutor = new ExecutorManager(ExecutorBuilder.newFixedExecutor(1, "jms_consumer"));

    public DefaultJmsConsumer(JmsTemplate jmsTemplate, JmsConsumerConfig config) {
        super(jmsTemplate, config);
    }

    @Override
    public Message syncReceive() {

        return jmsTemplate.receive(config.getDestination());
    }

    @Override
    public void asyncReceive(Consumer<Message> callback) {
        consumerExecutor.submit(() -> {
            while (true) {
                Message receive = jmsTemplate.receive(config.getDestination());
                callback.accept(receive);
            }
        });
    }
}