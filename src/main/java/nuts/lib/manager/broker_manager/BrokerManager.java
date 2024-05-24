package nuts.lib.manager.broker_manager;

public interface BrokerManager<
        P extends MessageProducer,
        PC extends ProducerConfig,
        C extends MessageConsumer,
        CC extends ConsumerConfig> {

    P getProducer(PC config);

    C getConsumer(CC config);
}
