package fi.utu.tech.telephonegame.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener implements Runnable {
	int serverPort;
	NetworkService network;
	
	public Listener(int serverPort, NetworkService network) {
		this.serverPort = serverPort;
		this.network = network;
	}
	
	@Override
	public void run() {
		// serversocketti kuuntelee vertaisia
		try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("A peer has successfully connected to me");
				
				network.CommsStarter(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}