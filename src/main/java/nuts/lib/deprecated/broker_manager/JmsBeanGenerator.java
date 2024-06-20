package nuts.lib.deprecated.broker_manager;

import jakarta.jms.ConnectionFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.apache.activemq.artemis.core.remoting.impl.netty.TransportConstants;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.Arrays;

/**
 * This is a generator class that is used when you want to manually register multiple beans for ActiveMQ use.
 *
 * @author nuts
 * @since 2024. 05. 29
 */

@Deprecated(since = "2024. 06. 20")
@Getter
public class JmsBeanGenerator {

    private final ConnectionFactory connectionFactory;

    public JmsBeanGenerator(String brokerUrl, String userName, String password) {
        this.connectionFactory = new CachingConnectionFactory(new ActiveMQConnectionFactory(brokerUrl, userName, password));
    }

    /**
     * A listener factory that is automatically set up via the JmsAnnotationDrivenConfiguration class.
     */
    public JmsListenerContainerFactory<?> getListenerContainerFactory(DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    public JmsTemplate getJmsTemplate() {

        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }


    /**
     * Set up distributed broker endpoints for high availability.
     * <p>
     * By default, it is set to send data to endpoints in a round-robin fashion,
     * but it does not guarantee the remnants of distributed systems such as replicas.
     * That is done through the server settings
     */
    public static ActiveMQConnectionFactory ActiveMqConnectionFactoryWithHA(ActiveMqHaEndpointConfig[] configs, String userName, String password) {

        TransportConfiguration[] configurations = Arrays.stream(configs).map(c -> {
            TransportConfiguration transportConfiguration = new TransportConfiguration(NettyConnectorFactory.class.getName());
            transportConfiguration.getParams().put(TransportConstants.HOST_PROP_NAME, c.hostName);
            transportConfiguration.getParams().put(TransportConstants.PORT_PROP_NAME, String.valueOf(c.portNumber));
            return transportConfiguration;
        }).toArray(TransportConfiguration[]::new);

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(true, configurations);
        connectionFactory.setUser(userName);
        connectionFactory.setPassword(password);

        return connectionFactory;
    }

    @Getter
    @AllArgsConstructor
    public static class ActiveMqHaEndpointConfig {
        String hostName;
        int portNumber;
    }

}