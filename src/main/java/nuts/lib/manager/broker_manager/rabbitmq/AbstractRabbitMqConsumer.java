package nuts.lib.manager.broker_manager.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import nuts.lib.manager.broker_manager.BrokerConsumer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

@Slf4j
public abstract class AbstractRabbitMqConsumer implements BrokerConsumer<Object> {

    private final RabbitTemplate rabbitTemplate;
    private final String queueName;
    private final Consumer<Object> callback = getCallback();
    private final ExecutorService consumerExecutor;
    private volatile boolean interrupted = false;
    private String poolName = "rabbitmq_consumer";
    private Thread workerThread;
    private int nThread = 1;

    public AbstractRabbitMqConsumer(RabbitTemplate rabbitTemplate, String queueName) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueName = queueName;
        this.consumerExecutor = Executors.newSingleThreadExecutor(getThreadFactory(this.poolName));
    }

    public AbstractRabbitMqConsumer(RabbitTemplate rabbitTemplate, String queueName, int nThread) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueName = queueName;
        this.nThread = nThread;
        this.consumerExecutor = Executors.newFixedThreadPool(nThread, getThreadFactory(this.poolName));
    }

    public AbstractRabbitMqConsumer(RabbitTemplate rabbitTemplate, String queueName, int nThread, String poolName) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueName = queueName;
        this.nThread = nThread;
        this.poolName = poolName;
        this.consumerExecutor = Executors.newFixedThreadPool(nThread, getThreadFactory(this.poolName));
    }

    @Override
    public void receive() {

        if (workerThread == null) {
            log.info("rabbitmq_consumer start (PoolName : {}, MaxThread : {})", poolName, nThread);
            for (int i = 0; i < nThread; i++) {
                consumerExecutor.submit(() -> {
                    try {
                        while (!interrupted) {
                            workerThread = Thread.currentThread();
                            Object received = rabbitTemplate.receiveAndConvert(queueName);
                            if (received != null) callback.accept(received);
                        }
                    } catch (Exception e) {
                        if (interrupted) {
                            log.info("rabbitmq_consumer interrupted , pool_name : {} ", poolName);
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

    protected abstract Consumer<Object> getCallback();

    private ThreadFactory getThreadFactory(String poolName) {

        return new ThreadFactory() {
            private int serial = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, poolName + "_" + serial++);
            }
        };
    }
}
