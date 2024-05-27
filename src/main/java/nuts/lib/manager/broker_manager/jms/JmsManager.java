package nuts.lib.manager.broker_manager.jms;

import nuts.lib.manager.broker_manager.BrokerManager;
import nuts.lib.manager.broker_manager.jms.config.JmsConsumerConfig;
import nuts.lib.manager.broker_manager.jms.config.JmsProducerConfig;
import nuts.lib.manager.broker_manager.jms.consumer.JmsConsumer;
import nuts.lib.manager.broker_manager.jms.producer.JmsProducer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;

/**
 * ActiveMQ is a client manager implementation of the message broker.
 *
 * @author nuts
 * @since 2024. 05. 27
 */
public class JmsManager implements BrokerManager<JmsProducer, JmsProducerConfig, JmsConsumer, JmsConsumerConfig> {

    private JmsTemplate jmsTemplate;

    @Override
    public <MPI extends JmsProducer> MPI getProducer(JmsProducerConfig config, Class<MPI> producerClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Assert.notNull(jmsTemplate, "no null");
        return producerClass.getConstructor(JmsTemplate.class, JmsProducerConfig.class).newInstance(jmsTemplate, config);
    }

    @Override
    public <MCI extends JmsConsumer> MCI getConsumer(JmsConsumerConfig config, Class<MCI> consumerClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Assert.notNull(jmsTemplate, "no null");
        return consumerClass.getConstructor(JmsTemplate.class, JmsConsumerConfig.class).newInstance(jmsTemplate, config);
    }

    public JmsManager postConstruct(JmsTemplate jmsTemplate) {
        jmsTemplate.setPubSubDomain(true);
        this.jmsTemplate = jmsTemplate;
        return this;
    }
}
