package com.ramonstudy.spring_rabbitmq.Topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiverTopic {

    private  static  String NAME_EXCHANGE = "MyTOPICExchange";


    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel  = connection.createChannel();
        String nameQueue = channel.queueDeclare().getQueue();
        String BINDING_KEY= "*.*.car";
        channel.exchangeDeclare(NAME_EXCHANGE,"topic" );
        channel.queueBind(nameQueue, NAME_EXCHANGE, BINDING_KEY);
        DeliverCallback deliverCallback = (ConsumerTag, delivery) ->{
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[+] Received message: ´" + message + "´" );

        };
        boolean AutoACK = true;
        channel.basicConsume(nameQueue,AutoACK, deliverCallback, ConsumerTag->{});
        
    }
}
