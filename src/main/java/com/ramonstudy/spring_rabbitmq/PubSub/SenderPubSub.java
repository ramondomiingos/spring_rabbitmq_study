package com.ramonstudy.spring_rabbitmq.PubSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SenderPubSub {

    private  static  String NAME_EXCHANGE = "FanoutExchange";

    public static void main(String[] args)  throws Exception{


        ConnectionFactory factory = new ConnectionFactory();
        // Todo: Mudar para variaveis de ambiente
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel  = connection.createChannel();
        channel.exchangeDeclare(NAME_EXCHANGE, "fanout");

        int i = 0;
        String message;
        LocalDateTime localDate;
        DateTimeFormatter formatter ;
        String formattedString ;
                while(i <3){
                    localDate = LocalDateTime.now();
                    formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    formattedString = localDate.format(formatter);
                    message = "..("+i+") - Hello,  message published with spring and RabbitMq!"+ NAME_EXCHANGE + " at "+ formattedString;
                    channel.basicPublish(NAME_EXCHANGE,"", null, message.getBytes());

                    System.out.println("[x] Sent ´"+message+"´");
                    Thread.sleep(1000);
                    i++;

                }



    }
}