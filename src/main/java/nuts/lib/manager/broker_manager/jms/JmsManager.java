package nuts.lib.manager.broker_manager.jms;

import nuts.lib.manager.broker_manager.BrokerManager;
import nuts.lib.manager.broker_manager.jms.config.JmsConsumerConfig;
import nuts.lib.manager.broker_manager.jms.config.JmsProducerConfig;
import nuts.lib.manager.broker_manager.jms.consumer.JmsConsumer;
import nuts.lib.manager.broker_manager.jms.producer.JmsProducer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.Assert;

public class JmsManager implements BrokerManager<JmsProducer, JmsProducerConfig, JmsConsumer, JmsConsumerConfig> {

    private JmsTemplate jmsTemplate;

    @Override
    public JmsProducer getProducer(JmsProducerConfig config) {
        Assert.notNull(jmsTemplate, "no null");

        return new JmsProducer(jmsTemplate, config);
    }

    @Override
    public JmsConsumer getConsumer(JmsConsumerConfig config) {
        return null;
    }

    public JmsManager postConstruct(JmsTemplate jmsTemplate){
        jmsTemplate.setPubSubDomain(true);
        this.jmsTemplate = jmsTemplate;
        return this;
    }
}
