package com.ramonstudy.spring_rabbitmq.WorkQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SenderWork {
private  static  String NAME_QUEUE = "_Work_";
    public static void main(String[] args)  throws Exception{


        ConnectionFactory factory = new ConnectionFactory();
        // Todo: Mudar para variaveis de ambiente
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel  = connection.createChannel();
        channel.queueDeclare(NAME_QUEUE, false, false, false, null);
        int i = 0;
        String message;
        LocalDateTime localDate;
        DateTimeFormatter formatter ;
        String formattedString ;
                while(i <10){
                    localDate = LocalDateTime.now();
                    formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    formattedString = localDate.format(formatter);
                    message = "..("+i+") - Hello,  message published with spring and RabbitMq! at "+ formattedString;
                    channel.basicPublish("",NAME_QUEUE, null, message.getBytes());

                    System.out.println("[x] Sent ´"+message+"´");
                    Thread.sleep(1000);
                    i++;

                }



    }
}