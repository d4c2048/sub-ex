package com.lee.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

import static com.lee.utils.Constants.CANCEL_QUEUE_NAME;
import static com.lee.utils.Constants.SELECT_QUEUE_NAME;

/**
 * The type Active mq configuration.
 *
 * @author Lee
 */
@Configuration
public class ActiveMqConfiguration {

    /**
     * Queue 1 queue.
     *
     * @return the queue
     */
    @Bean(value = "select")
    public Queue queue1() {
        return new ActiveMQQueue(SELECT_QUEUE_NAME);
    }

    /**
     * Queue 2 queue.
     *
     * @return the queue
     */
    @Bean(value = "cancel")
    public Queue queue2() {
        return new ActiveMQQueue(CANCEL_QUEUE_NAME);
    }



}
