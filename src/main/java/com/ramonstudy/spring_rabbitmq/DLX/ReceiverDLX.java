package com.ramonstudy.spring_rabbitmq.DLX;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiverDLX {
    public static final String CONSUMER_QUEUE = "queueConsumer" ;



    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel  = connection.createChannel();
        String nameQueue = channel.queueDeclare().getQueue();

        DeliverCallback deliverCallback = (ConsumerTag, delivery) ->{
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[+] Received message: ´" + message + "´" );


        };

        channel.basicConsume(CONSUMER_QUEUE,true, deliverCallback, ConsumerTag->{});
        
    }
}
