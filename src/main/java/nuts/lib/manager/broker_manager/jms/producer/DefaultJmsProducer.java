package nuts.lib.manager.broker_manager.jms.producer;

import nuts.lib.manager.broker_manager.jms.config.JmsProducerConfig;
import nuts.lib.manager.executor_manager.ScheduleExecutorManager;
import nuts.lib.manager.executor_manager.executor.ExecutorBuilder;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;

public class DefaultJmsProducer extends JmsProducer {

    private final ScheduleExecutorManager producerScheduler = new ScheduleExecutorManager(() -> ExecutorBuilder.newSingleThreadScheduledExecutor("jms_producer"));

    public DefaultJmsProducer(JmsTemplate jmsTemplate, JmsProducerConfig config) {
        super(jmsTemplate, config);
    }

    @Override
    public void send(Object message) {
        jmsTemplate.convertAndSend(config.getDestination(), message);
    }


    public void sendOnScheduler(Object message, long mills) {
        producerScheduler.schedule(() -> jmsTemplate.convertAndSend(config.getDestination(), message), mills, "sendScheduler");
    }

    public void sendOnScheduler(List<Object> message, long mills) {
        new Thread(() -> {
            for (Object o : message) {
                jmsTemplate.convertAndSend(config.getDestination(), o);
                try {
                    Thread.sleep(mills);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
