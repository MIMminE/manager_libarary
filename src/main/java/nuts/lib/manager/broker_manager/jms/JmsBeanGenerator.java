package nuts.lib.manager.broker_manager.jms;

import jakarta.jms.ConnectionFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.api.core.client.ActiveMQClient;
import org.apache.activemq.artemis.api.core.client.ServerLocator;
import org.apache.activemq.artemis.core.remoting.impl.TransportConfigurationUtil;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.apache.activemq.artemis.core.remoting.impl.netty.TransportConstants;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JmsAnnotationDrivenConfiguration
 */
@Getter
public class JmsBeanGenerator {

    private final ConnectionFactory connectionFactory;

    public JmsBeanGenerator(String brokerUrl, String userName, String password) {
        this.connectionFactory = new CachingConnectionFactory(new ActiveMQConnectionFactory(brokerUrl, userName, password));
    }

    /**
     * JmsAnnotationDrivenConfiguration
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


    public static ActiveMQConnectionFactory ActiveMqConnectionFactoryWithHA(ActiveMqHaEndpointConfig... configs) {

        Arrays.stream(configs).map(c -> {
            TransportConfiguration transportConfiguration = new TransportConfiguration(NettyConnectorFactory.class.getName());
            transportConfiguration.getParams().put(TransportConstants.HOST_PROP_NAME, c.hostName);
            transportConfiguration.getParams().put(TransportConstants.PORT_PROP_NAME, String.valueOf(c.portNumber));
            return transportConfiguration;
        }).toArray();


        return new ActiveMQConnectionFactory(true, a);
    }

    @Getter
    @AllArgsConstructor
    public static class ActiveMqHaEndpointConfig {
        String hostName;
        int portNumber;
    }

    public void te() {
        TransportConfiguration configurationHa1 = new TransportConfiguration(NettyConnectorFactory.class.getName());
        configurationHa1.getParams().put(TransportConstants.HOST_PROP_NAME, "localhost");
        configurationHa1.getParams().put(TransportConstants.PORT_PROP_NAME, "61616");

        TransportConfiguration configurationHa2 = new TransportConfiguration(NettyConnectorFactory.class.getName());
        configurationHa2.getParams().put(TransportConstants.HOST_PROP_NAME, "localhost");
        configurationHa2.getParams().put(TransportConstants.PORT_PROP_NAME, "61617");

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(true, configurationHa1, configurationHa2);

    }


}