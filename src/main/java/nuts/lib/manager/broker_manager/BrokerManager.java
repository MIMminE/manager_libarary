package nuts.lib.manager.broker_manager;

import java.lang.reflect.InvocationTargetException;

/**
 * This is the interface for the client implementation of the message broker server.
 * Among other types of brokers, it provides implementations for RabbitMQ, ActiveMQ, and Kafka.
 * <p>
 * Implementations to refer to
 * {@link nuts.lib.manager.broker_manager.jms.JmsManager}
 *
 * @param <MP>  Enter a class type that acts as a producer to send broker-specific messages.
 * @param <MPC> Enter the configuration class type of the producer for each broker.
 * @param <MC>  Enter the class of the consumer role to receive broker-specific messages.
 * @param <MCC> Enter the consumer configuration class type for each broker.
 * @author nuts
 * @since 2024. 05. 27
 */
public interface BrokerManager<
        MP extends MessageProducer<?>,
        MPC extends ProducerConfig<MP>,
        MC extends MessageConsumer<?>,
        MCC extends ConsumerConfig<MC>> {

    /**
     * Returns an instance of the Producer class that the BrokerManager creates and forwards.
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
}
