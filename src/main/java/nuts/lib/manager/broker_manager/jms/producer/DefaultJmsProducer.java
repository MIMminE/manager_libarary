package nuts.lib.manager.broker_manager.jms.producer;

import nuts.lib.manager.broker_manager.jms.config.JmsProducerConfig;
import nuts.lib.manager.executor_manager.ScheduleExecutorManager;
import nuts.lib.manager.executor_manager.executor.ExecutorBuilder;
import nuts.lib.manager.executor_manager.executor.thread_factory.UncaughtExceptionThreadFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DefaultJmsProducer extends JmsProducer {

    private final ScheduleExecutorManager producerScheduler = new ScheduleExecutorManager(() -> ExecutorBuilder.newSingleThreadScheduledExecutor("jms_producer"));

    public DefaultJmsProducer(JmsTemplate jmsTemplate, JmsProducerConfig config) {
        super(jmsTemplate, config);
    }

    @Override
    public void send(Object message) {
        jmsTemplate.convertAndSend(config.getDestination(), message);
    }


    public void sendOnScheduler(Object message, long mills) throws ExecutionException, InterruptedException {
        producerScheduler.schedule(() -> jmsTemplate.convertAndSend(config.getDestination(), message), mills, "sendScheduler");
    }

    public void sendOnScheduler(List<Object> message, long mills) {
        new Thread(() -> {
            for (Object o : message) {
                jmsTemplate.convertAndSend(config.getDestination(), o);
            }
        }).start();
    }
}
