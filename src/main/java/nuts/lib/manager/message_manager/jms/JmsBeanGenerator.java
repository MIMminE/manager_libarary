package nuts.lib.manager.message_manager.jms;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * JmsAnnotationDrivenConfiguration
 *
 */
public class JmsBeanGenerator {

    private final ConnectionFactory connectionFactory;

    public ConnectionFactory getConnectionFactory(){
        return connectionFactory;
    }

    public JmsBeanGenerator(String brokerUrl, String userName, String password) {
        this.connectionFactory = new CachingConnectionFactory(new ActiveMQConnectionFactory(brokerUrl, userName, password));
    }

    /**
     *  JmsAnnotationDrivenConfiguration
     */
    public JmsListenerContainerFactory<?> getListenerContainerFactory(DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    public JmsTemplate getJmsTemplate(){

        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }

}
