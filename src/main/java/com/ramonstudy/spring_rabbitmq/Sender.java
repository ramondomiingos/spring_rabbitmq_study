package com.ramonstudy.spring_rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
private  static  String NAME_QUEUE = "hELLO";
    public static void main(String[] args)  throws Exception{
        // Criar a conexão
        // setar as informações para criar
        // criar um novo canal
        // declarar a fila
        //criar mensagem
        //enviar mensagem

        ConnectionFactory factory = new ConnectionFactory();
        // Todo: Mudar para variaveis de ambiente
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel  = connection.createChannel();
        channel.queueDeclare(NAME_QUEUE, false, false, false, null);
        String message = "Hello, this is a  first message published with spring and RabbitMq!";
        channel.basicPublish("",NAME_QUEUE, null, message.getBytes());
        System.out.println("[x] Sent ´"+message+"´");


    }
}