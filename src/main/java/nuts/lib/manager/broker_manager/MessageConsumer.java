package nuts.lib.manager.broker_manager;

import java.util.function.Consumer;

public abstract class MessageConsumer<M> {

    public M syncReceive() {
        return null;
    }

    public void asyncReceive(Consumer<M> callback) {
    }
}
