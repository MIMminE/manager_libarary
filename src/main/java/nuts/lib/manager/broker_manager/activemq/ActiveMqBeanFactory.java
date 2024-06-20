package nuts.lib.manager.broker_manager.activemq;

import jakarta.jms.ConnectionFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.apache.activemq.artemis.core.remoting.impl.netty.TransportConstants;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.jms.connection.CachingConnectionFactory;

import java.util.Arrays;

public abstract class ActiveMqBeanFactory {

    public static final String DEFAULT_NETWORK_BROKER_URL = "tcp://localhost:61616";

    public static ConnectionFactory defaultConnectionFactory(String brokerUrl, String userName, String password) {
        return new CachingConnectionFactory(new ActiveMQConnectionFactory(brokerUrl, userName, password));
    }

    /**
     * Set up distributed broker endpoints for high availability.
     * <p>
     * By default, it is set to send data to endpoints in a round-robin fashion,
     * but it does not guarantee the remnants of distributed systems such as replicas.
     * That is done through the server settings
     */
    public static ActiveMQConnectionFactory ActiveMqConnectionFactoryWithHA(ActiveMqBeanFactory.ActiveMqHaEndpointConfig[] configs, String userName, String password) {

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
