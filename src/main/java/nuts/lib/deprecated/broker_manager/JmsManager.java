package nuts.lib.deprecated.broker_manager;

import nuts.lib.deprecated.broker_manager.producer.JmsProducer;
import nuts.lib.deprecated.broker_manager.consumer.JmsConsumer;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;

/**
 * ActiveMQ is a client manager implementation of the message broker.
 *
 * @author nuts
 * @since 2024. 05. 27
 */
@Deprecated(since = "2024. 06. 20")
public class JmsManager implements BrokerManager<JmsProducer, JmsProducer.JmsProducerConfig, JmsConsumer, JmsConsumer.JmsConsumerConfig> {

    private JmsTemplate jmsTemplate;

    @Override
    public <MPI extends JmsProducer> MPI getProducer(JmsProducer.JmsProducerConfig config, Class<MPI> producerClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Assert.notNull(jmsTemplate, "no null");
        return producerClass.getConstructor(JmsTemplate.class, JmsProducer.JmsProducerConfig.class).newInstance(jmsTemplate, config);
    }

    @Override
    public <MCI extends JmsConsumer> MCI getConsumer(JmsConsumer.JmsConsumerConfig config, Class<MCI> consumerClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Assert.notNull(jmsTemplate, "no null");
        return consumerClass.getConstructor(JmsTemplate.class, JmsConsumer.JmsConsumerConfig.class).newInstance(jmsTemplate, config);
    }

    public JmsManager postConstruct(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        return this;
    }

    /**
     * By default, it supports the anycast method,
     * <p>
     * but it also supports the topic format like Kafka's way of working. To do this, we need to give the second argument true.
     */
    public JmsManager postConstruct(JmsTemplate jmsTemplate, boolean topicModeEnable) {
        jmsTemplate.setPubSubDomain(topicModeEnable);
        this.jmsTemplate = jmsTemplate;
        return this;
    }

    public JmsManager postConstruct(String brokerUrl, String userName, String password) {
        this.jmsTemplate = new JmsTemplate(new CachingConnectionFactory(new ActiveMQConnectionFactory(brokerUrl, userName, password)));
        return this;
    }
}
