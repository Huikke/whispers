package fi.utu.tech.telephonegame.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.io.Serializable;

public class Listener implements Runnable {
	int serverPort;
	
	//aloitetut yhteys_säikeet kuuntelevat tätä 
	private TransferQueue<Object> outQueue;
	
	public Listener(int serverPort) {
		this.serverPort = serverPort;
	}
	
	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("A peer has successfully connected to me");
				OutgoingFeed outFeed = new OutgoingFeed(socket,outQueue);
				
				//tehdään säie palvelemaan yhtä vertaista
				Thread outFeedThread = new Thread(outFeed);
				outFeedThread.setDaemon(true);
				outFeedThread.start();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//lisätään viesti outQueue:een
	public void send(Serializable out) {
		try {
			outQueue.offer(out, 1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}