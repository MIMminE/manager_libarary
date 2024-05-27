package nuts.lib.manager.broker_manager.kafka.producer;

import nuts.lib.manager.broker_manager.MessageProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public abstract class KafkaProducer extends MessageProducer<ProducerRecord<?, ?>> {

    @Override
    public void send(ProducerRecord<?, ?> message) {

    }
}
