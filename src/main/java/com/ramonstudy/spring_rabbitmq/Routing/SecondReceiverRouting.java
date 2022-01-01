package com.ramonstudy.spring_rabbitmq.Routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class SecondReceiverRouting {
    private static String BINDING_KEY_NAME = "SecondRoutinKeyExample";
    private  static  String NAME_EXCHANGE = "DirectExchange";


    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel  = connection.createChannel();
        String nameQueue = channel.queueDeclare().getQueue();

        channel.exchangeDeclare(NAME_EXCHANGE,"direct" );
        channel.queueBind(nameQueue, NAME_EXCHANGE, BINDING_KEY_NAME);
        DeliverCallback deliverCallback = (ConsumerTag, delivery) ->{
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[+] Received message: ´" + message + "´" );

        };
        boolean AutoACK = true;
        channel.basicConsume(nameQueue,AutoACK, deliverCallback, ConsumerTag->{});
        
    }
}
