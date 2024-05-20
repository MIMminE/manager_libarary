package nuts.lib.manager.message_manager.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class JmsManager {

    private final JmsTemplate jmsTemplate;
    private final ExecutorService executors = Executors.newFixedThreadPool(10, new JmsThreadFactory());


    public void sendMessage(String destinationName, Object message) {
        jmsTemplate.convertAndSend(destinationName, message);
    }

    public void asyncReceiveDestination(String destinationName, Consumer<Message> action) {


        executors.submit(
                () -> {
                    while (true) {
                        Message receive = jmsTemplate.receive(destinationName);
                        action.accept(receive);
                    }
                });
    }


    static public Runnable receiveDestination(String destination, JmsTemplate jmsTemplate) {
        return () -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    System.out.println("current thread =" + Thread.currentThread().getName() + " destination = " + destination);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Message receive = jmsTemplate.receive(destination);
                try {
                    String body = receive.getBody(String.class);
                    System.out.println(body);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
