package com.microservico.tcptorabbitmq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class TcpToTabbitMQApplication {

	private static final String QUEUE_NAME = "tcp-to-rabbitmq-queue";
	private static final int PORT = 8080;

	public static void main(String[] args) {

		try(ServerSocket serverSocket = new ServerSocket(PORT)) {

			log.info("TCP Server Started on port: " + PORT);

			while(true) {
				new ClientHandler(serverSocket.accept()).start();
			}
			
		} catch(IOException e) {

		}

	}

	static class ClientHandler extends Thread {

		private final Socket clientSocket;

		ClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		@Override
		public void run() {

			try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

				String message = in.readLine();
				sendToRabbitMQ(message);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		private void sendToRabbitMQ(String message) throws Exception {

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("rabbitmq");
			
			try(

				Connection connection = factory.newConnection();
				Channel channel = connection.createChannel();

			) {

				channel.queueDeclare(QUEUE_NAME, false, false, false, null);
				channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
				log.info("Sent: '" + message + "'");

			}

		}

	}

}
