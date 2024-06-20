package nuts.lib.deprecated.broker_manager.consumer;

import jakarta.jms.Message;
import nuts.lib.manager.executor_manager.ExecutorManager;
import nuts.lib.manager.executor_manager.executor.ExecutorBuilder;
import org.springframework.jms.core.JmsTemplate;

import java.util.function.Consumer;

@Deprecated(since = "2024. 06. 20")
public class DefaultJmsConsumer extends JmsConsumer {

    public DefaultJmsConsumer(JmsTemplate jmsTemplate, JmsConsumerConfig config) {
        super(jmsTemplate, config);
    }

    private final ExecutorManager consumerExecutor = new ExecutorManager(ExecutorBuilder.newFixedExecutor(1, "jms_consumer"));

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