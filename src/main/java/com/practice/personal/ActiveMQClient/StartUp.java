package com.practice.personal.ActiveMQClient;

import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.print.attribute.standard.Destination;

/**
 * Hello world!
 *
 */
public class StartUp {
	public static void main(String[] args) throws Exception {
		
		Session session = null;
		Connection connection = null;
		try {
			
			//Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Properties env = new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
			env.put(Context.PROVIDER_URL, "tcp://localhost:61616");
			env.put("queue.TEST001", "TEST001");
			Context namingContext = new InitialContext(env);
			ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup("ConnectionFactory");
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("TEST001");
			MessageConsumer consumer = session.createConsumer(queue);
			connection.start();
			
			while(true){
				TextMessage textMsg = (TextMessage)consumer.receive();
				System.out.println(textMsg);
				System.out.println("Received: " + textMsg.getText());
				if(textMsg.getText().equals("END")) {
					break;
				}
				
			}
			
			//MessageConsumer consumer = new Consum
		} catch (Exception ex) {
				throw ex;
		}finally {
			if(session != null) {
				session.close();
			}
			if(connection != null) {
				connection.close();
			}
		}

	}
}
