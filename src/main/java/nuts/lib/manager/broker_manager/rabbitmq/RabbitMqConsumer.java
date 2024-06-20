package nuts.lib.manager.broker_manager.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitMqConsumer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
