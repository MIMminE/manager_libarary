package nuts.lib.manager.message_manager;

public interface MessageManager<C extends Consumer, P extends publisher> {

    C getConsumer();

    P getProducer();
}
