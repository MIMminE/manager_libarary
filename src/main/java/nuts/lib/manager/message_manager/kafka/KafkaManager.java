package nuts.lib.manager.message_manager.kafka;

import nuts.lib.manager.message_manager.MessageManager;

public class KafkaManager implements MessageManager<KafkaConsumer, KafkaProducer> {


    @Override
    public KafkaConsumer getConsumer() {
        return null;
    }

    @Override
    public KafkaProducer getProducer() {
        return null;
    }
}
