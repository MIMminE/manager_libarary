package nuts.lib.manager.message_manager.jms;

import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class JmsManager {

    private final JmsTemplate jmsTemplate;
    private final ExecutorService executors = Executors.newCachedThreadPool(new JmsThreadFactory());


    public void sendMessage(String destinationName, Object message) {
        jmsTemplate.convertAndSend(destinationName, message);
    }

    public void asyncReceiveDestination(String destinationName, Consumer<Message> action) {

        executors.submit(() -> {
            while (true) {
                Message receive = jmsTemplate.receive(destinationName);
                action.accept(receive);
            }
        });
    }
}
