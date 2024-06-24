package nuts.lib.manager.broker_manager.activemq.consumer;

import jakarta.jms.Message;
import lombok.extern.slf4j.Slf4j;
import nuts.lib.manager.broker_manager.BrokerConsumer;
import nuts.lib.manager.executor_manager.ExecutorManager;
import nuts.lib.manager.executor_manager.executor.ExecutorBuilder;
import org.springframework.jms.core.JmsTemplate;

import java.util.function.Consumer;

@Slf4j
abstract class AbstractActiveMqConsumer implements BrokerConsumer<Message> {
    private String poolName = "activemq_consumer";
    private ExecutorManager consumerExecutor = new ExecutorManager(ExecutorBuilder.newFixedExecutor(20, poolName));
    private volatile boolean interrupted = false;
    private Thread workerThread;
    protected Consumer<Message> callback;

    private final JmsTemplate jmsTemplate;
    private final String destination;

    public AbstractActiveMqConsumer(JmsTemplate jmsTemplate, String destination, String poolName) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
        this.poolName = poolName;
    }

    @Override
    public synchronized void receive() {
        if (workerThread == null) {
            for (int i = 0; i < 1; i++){

                consumerExecutor.submit(() -> {
                    try {
                        while (!interrupted) {
                            workerThread = Thread.currentThread();
                            Message receive = jmsTemplate.receive(destination);
                            callback.accept(receive);
                            debugLogPrint(receive);
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                        if (interrupted) {
                            log.info("activemq_consumer interrupted , pool_name : {} ", poolName);
                            interrupted = true;
                            workerThread = null;
                        }
                    }
                });
            }
        } else
            throw new RuntimeException("It's already working.");
    }


    public synchronized void interrupt() {
        this.interrupted = true;
        if (workerThread != null) this.workerThread.interrupt();
    }


    protected void debugLogPrint(Message message) {
        log.debug("activemq_consumer receive message : {}", message);
    }
}
