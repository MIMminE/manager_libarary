package nuts.lib.manager.broker_manager.kafka.consumer;

import nuts.lib.manager.broker_manager.MessageConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class KafkaConsumer extends MessageConsumer<ConsumerRecords<?, ?>> {

    @Override
    public ConsumerRecords<?, ?> syncReceive() {
        return null;
    }

    @Override
    public void asyncReceive(Consumer<ConsumerRecords<?, ?>> callback) {

    }
}
