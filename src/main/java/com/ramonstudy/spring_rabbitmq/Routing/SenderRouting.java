package com.ramonstudy.spring_rabbitmq.Routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SenderRouting {
    private static String ROUTING_KEY = "RoutinKeyExample";
    private static String ROUTING_KEY_2 = "SecondRoutinKeyExample";
    private  static  String NAME_EXCHANGE = "DirectExchange";

    public static void main(String[] args)  throws Exception{


        ConnectionFactory factory = new ConnectionFactory();
        // Todo: Mudar para variaveis de ambiente
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel  = connection.createChannel();
        channel.exchangeDeclare(NAME_EXCHANGE, "direct");

        int i = 0;
        String message;
        LocalDateTime localDate;
        DateTimeFormatter formatter ;
        String formattedString ;
                while(i <10){
                    localDate = LocalDateTime.now();
                    formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    formattedString = localDate.format(formatter);
                    message = "..("+i+") - Hello,  message published with spring and RabbitMq!"+ NAME_EXCHANGE + " at "+ formattedString;
                    if(i%2 == 0 ){
                        channel.basicPublish(NAME_EXCHANGE,ROUTING_KEY, null, message.getBytes());
                    }else{
                        channel.basicPublish(NAME_EXCHANGE,ROUTING_KEY_2, null, message.getBytes());

                    }

                    System.out.println("[x] Sent ´"+message+"´");
                    Thread.sleep(1000);
                    i++;

                }



    }
}