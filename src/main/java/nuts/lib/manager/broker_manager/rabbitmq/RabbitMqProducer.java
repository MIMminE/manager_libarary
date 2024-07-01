package nuts.lib.manager.broker_manager.rabbitmq;

import nuts.lib.manager.broker_manager.BrokerProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitMqProducer implements BrokerProducer<Object> {

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;

    public RabbitMqProducer(RabbitTemplate rabbitTemplate, String exchangeName, String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    }

    @Override
    public void send(Object message) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}
