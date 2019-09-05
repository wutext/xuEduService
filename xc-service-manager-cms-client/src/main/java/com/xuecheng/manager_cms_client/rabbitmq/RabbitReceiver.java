package com.xuecheng.manager_cms_client.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class RabbitReceiver {

    /**
     * 传递实体监听有问题
     * @param
     * @param channel
     * @param
     * @throws IOException
     */
    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
                    declare = "${spring.rabbitmq.listener.order.queue.declare}"),
            exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
                    declare = "${spring.rabbitmq.listener.order.exchange.declare}",
                    durable= "${spring.rabbitmq.listener.order.exchange.durable}",
                    type="${spring.rabbitmq.listener.order.exchange.type}",
                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.declare}"),
            key = "${spring.rabbitmq.listener.order.key}"
        )
    )
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws IOException {

        System.out.println("---------------------------");
        System.out.println("消费者PayLoad="+message.getPayload());
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        System.out.println(deliveryTag);
        channel.basicAck(deliveryTag, false);
    }

}
