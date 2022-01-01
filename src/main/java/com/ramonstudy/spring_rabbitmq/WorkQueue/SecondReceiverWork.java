package com.ramonstudy.spring_rabbitmq.WorkQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class SecondReceiverWork {
    private  static  String NAME_QUEUE = "_Work_";

    private static void doWork(String task) throws  Exception{
        System.out.println("[*] Processing ....´");
        for (char ch:task.toCharArray()){
            if(ch == '.'){
                System.out.println("[*]");

                Thread.sleep(1000);
            }
        }
    }
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        // Todo: Mudar para variaveis de ambiente
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel  = connection.createChannel();
        channel.queueDeclare(NAME_QUEUE, false, false, false, null);
        DeliverCallback deliverCallback = (ConsumerTag, delivery) ->{
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[+] Received message: ´" + message + "´" );
            try {
                doWork(message);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("[x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        boolean AutoACK = false;
        channel.basicConsume(NAME_QUEUE,AutoACK, deliverCallback, ConsumerTag->{});
        
    }
}
