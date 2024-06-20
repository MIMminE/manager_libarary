package nuts.lib.manager.broker_manager;

public interface BrokerProducer<M> {

    void send(M message);
}
