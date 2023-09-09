package com.jd.dojbackendjudgeservice.rabbitmq;

import com.jd.dojbackendjudgeservice.judge.JudgeService;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class MyMessageConsumer {

    @Resource
    private JudgeService judgeService;

    // 指定程序监听的消息队列和确认机制
    @SneakyThrows
    @RabbitListener(queues = {"code_queue"}, ackMode = "MANUAL") // MANUAL：手动确认
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliverTag) {
        log.info("receiveMessage message = {}", message);
        long questionSubmitId = Long.parseLong(message);
        try {
            judgeService.doJudge(questionSubmitId);
            channel.basicAck(deliverTag, false);
        } catch (Exception e) {
            // multiple：一个布尔值，标识是否拒绝多个消息。如果设置为 true，则会拒绝指定 deliverTag 及其之前未被确认的所有消息；如果设置为 false，则只拒绝指定 deliverTag 的消息。
            channel.basicNack(deliverTag, false, false);
        }
    }
}
