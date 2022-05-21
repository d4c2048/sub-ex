package com.lee.producer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Queue;

import static com.lee.utils.Constants.CANCEL_QUEUE_NAME;
import static com.lee.utils.Constants.SELECT_QUEUE_NAME;

/**
 * The type Mq producer.
 *
 * @author Lee
 */
@Component
public class MqProducer {

    /**
     * Mq中的选课队列
     */
    @Resource
    @Qualifier(SELECT_QUEUE_NAME)
    private Queue select;

    /**
     * Mq中的退课队列
     */
    @Resource
    @Qualifier(CANCEL_QUEUE_NAME)
    private Queue cancel;

    /**
     * Jms服务对象
     */
    @Resource
    JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 发送选课消息
     * @param msg 消息
     */
    public void sendSelectMessage(String msg) {
        jmsMessagingTemplate.convertAndSend(select, msg);
    }

    /**
     * 发送退课消息
     * @param msg 消息
     */
    public void sendCancelMessage(String msg) {
        jmsMessagingTemplate.convertAndSend(cancel, msg);
    }

}
