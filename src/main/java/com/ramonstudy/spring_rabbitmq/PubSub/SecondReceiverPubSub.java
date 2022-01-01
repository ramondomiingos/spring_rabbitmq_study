package com.ramonstudy.spring_rabbitmq.PubSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class SecondReceiverPubSub {


    private  static  String NAME_EXCHANGE = "FanoutExchange";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel  = connection.createChannel();
        String nameQueue = "BRODCAST!" ;
        channel.queueDeclare(nameQueue, false, false, false, null);
        channel.exchangeDeclare(NAME_EXCHANGE,"fanout" );
        channel.queueBind(nameQueue, NAME_EXCHANGE, "");
        DeliverCallback deliverCallback = (ConsumerTag, delivery) ->{
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[+] Received message: ´" + message + "´" );

        };
        boolean AutoACK = true;
        channel.basicConsume(nameQueue,AutoACK, deliverCallback, ConsumerTag->{});
    }

}
