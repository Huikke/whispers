package fi.utu.tech.telephonegame.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.io.Serializable;

public class Listener implements Runnable {
	int serverPort;
	
	//aloitetut yhteys_säikeet kuuntelevat tätä 
	private TransferQueue<Serializable> outQueue;
	private TransferQueue<Object> inQueue;
	
	public Listener(int serverPort, TransferQueue<Serializable> outQueue, TransferQueue<Object> inQueue) {
		this.serverPort = serverPort;
		this.outQueue = outQueue;
		this.inQueue = inQueue;
	}
	
	@Override
	public void run() {
		// serversocketti kuuntelee vertaisia
		try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("A peer has successfully connected to me");
				
				// Säie lähetystä varten
				OutgoingFeed outFeed = new OutgoingFeed(socket, outQueue);
				Thread outFeedThread = new Thread(outFeed);
				outFeedThread.setDaemon(true);
				outFeedThread.start();
				
				// Säie kuuntelua varten
				IncomingFeed feed = new IncomingFeed(socket, inQueue);
				Thread feedThread = new Thread(feed);
				feedThread.setDaemon(true);
				feedThread.start();
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