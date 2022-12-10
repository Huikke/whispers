package fi.utu.tech.telephonegame.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener implements Runnable {
	int serverPort;
	
	public Listener(int serverPort) {
		this.serverPort = serverPort;
	}
	
	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("A peer has successfully connected to me");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}