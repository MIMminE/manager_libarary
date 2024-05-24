package nuts.lib.manager.broker_manager;

public abstract class MessageBrokerManagerBuilder {

    public static <T extends BrokerManager<?, ?, ?, ?>> T buildMessageManager(MessageBasicConfig config, Class<T> brokerClass) {

        try {
            return brokerClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
