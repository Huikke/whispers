package fi.utu.tech.telephonegame.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.io.Serializable;

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