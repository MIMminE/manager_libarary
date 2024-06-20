package nuts.lib.manager.broker_manager.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitMqProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
