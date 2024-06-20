package nuts.lib.deprecated.broker_manager;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

/**
 * This is the interface for the client implementation of the message broker server.
 * Among other types of brokers, it provides implementations for RabbitMQ, ActiveMQ, and Kafka.
 * <p>
 * Implementations to refer to
 * {@link JmsManager}
 *
 * @param <MP>  Enter a class type that acts as a producer to send broker-specific messages.
 * @param <MPC> Enter the configuration class type of the producer for each broker.
 * @param <MC>  Enter the class of the consumer role to receive broker-specific messages.
 * @param <MCC> Enter the consumer configuration class type for each broker.
 * @author nuts
 * @since 2024. 05. 27
 */
@Deprecated(since = "2024. 06. 20")
public interface BrokerManager<
        MP extends BrokerManager.MessageProducer<?>,
        MPC extends BrokerManager.ProducerConfig<MP>,
        MC extends BrokerManager.MessageConsumer<?>,
        MCC extends BrokerManager.ConsumerConfig<MC>> {

    /**
     * Returns an instance of the BrokerProducer class that the BrokerManager creates and forwards.
     * The second argument is to enter the meta information of the actual implementation class for each broker.
     * <p>
     * It uses the reflection function to dynamically create instances, and exceptions are required accordingly
     */
    <MPI extends MP> MPI getProducer(MPC config, Class<MPI> producerClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * Returns an instance of the Consumer class that the BrokerManager creates and forwards.
     * The second argument is to enter the meta information of the actual implementation class for each broker.
     * <p>
     * It uses the reflection function to dynamically create instances, and exceptions are required accordingly
     */
    <MCI extends MC> MCI getConsumer(MCC config, Class<MCI> consumerClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    abstract class ConsumerConfig<MC> {
    }

    abstract class MessageConsumer<M> {

        public M syncReceive() {
            return null;
        }

        public void asyncReceive(Consumer<M> callback) {
        }
    }

    abstract class MessageProducer<M> {

        public void send(M message) {
        }
    }

    abstract class ProducerConfig<MP> {
    }
}
