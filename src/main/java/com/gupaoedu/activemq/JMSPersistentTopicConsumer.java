package com.gupaoedu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSPersistentTopicConsumer {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory
                        ("tcp://localhost:61616");
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.setClientID("Mic-001");

            connection.start();

            Session session = connection.createSession
                    (Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);
            //创建目的地
            Topic destination = session.createTopic("myTopic");
            //创建发送者
            MessageConsumer consumer = session.createDurableSubscriber(destination, "Mic-001");

            TextMessage textMessage = (TextMessage) consumer.receive();
            System.out.println(textMessage.getText());
            //消息被确认，当Boolean.TRUE, Session.AUTO_ACKNOWLEDGE时，需要commit
//            session.commit();
            textMessage.acknowledge();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
