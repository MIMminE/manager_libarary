package nuts.lib.manager.message_manager;

public interface MessageManager<C extends Consumer, P extends Producer> {

    C getConsumer();

    P getProducer();
}
