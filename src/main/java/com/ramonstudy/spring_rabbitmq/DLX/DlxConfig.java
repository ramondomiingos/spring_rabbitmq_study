package com.ramonstudy.spring_rabbitmq.DLX;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class DlxConfig {
    public static final String DLX_NAME = "dlxExchange" ;
    public static final String DLX_QUEUE = "dlxQueue" ;
    public static final String DLX_BINDING_KEY= "dlxRK" ;

    //Exchange do sistema
    public static final String EXCHANGE_NAME = "mainExchange" ;
    // Consumer
    public static final String CONSUMER_QUEUE = "queueConsumer" ;
    public static final String CONSUMER_BINDING_KEY = "bkConsumer" ;

    public static void main(String[] args) throws  Exception{
        ConnectionFactory factory = new ConnectionFactory();
        // Todo: Mudar para variaveis de ambiente
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel  = connection.createChannel();

        //Declarar exchanges (main e DLX)
        channel.exchangeDeclare(DLX_NAME, "topic");
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        //Declarar as filas ( consumes e dlx)

        channel.queueDeclare(DLX_QUEUE, false, false, false, null);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("x-message-ttl",10000);
        map.put("x-dead-letter-exchange",DLX_NAME);
        map.put("x-dead-letter-routing-key",DLX_BINDING_KEY);
        channel.queueDeclare(CONSUMER_QUEUE, false, false, false, map);

        //bindingkey da dlx e consumer
        channel.queueBind(DLX_QUEUE,DLX_NAME,DLX_BINDING_KEY+".#");
        channel.queueBind(CONSUMER_QUEUE,EXCHANGE_NAME,CONSUMER_BINDING_KEY+".#");

        connection.close();

    }
}
