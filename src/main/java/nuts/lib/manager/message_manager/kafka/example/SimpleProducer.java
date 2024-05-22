package nuts.lib.manager.message_manager.kafka.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class SimpleProducer {


    public void test() {
        Properties props = new Properties();
        //bootstrap.servers -> key.serializer.class ,
        props.setProperty("bootstrap.servers", "192.163.1.2:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);

        kafkaProducer.send(new ProducerRecord<String, String>("simple-topic","heelo"));

        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
