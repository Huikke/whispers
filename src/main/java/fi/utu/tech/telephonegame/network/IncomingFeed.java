package fi.utu.tech.telephonegame.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
//import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

import fi.utu.tech.telephonegame.Message;

/*
 * tämä luokka edustaa kuuntelu_yhteyttä vertaiseen
 */
public class IncomingFeed implements Runnable {
	
	private String peerIP;
	private int peerPort;
	private TransferQueue<Object> inQueue;
	
	public IncomingFeed(String peerIP, int peerPort,TransferQueue<Object> inQueue) {
		this.peerIP = peerIP;
		this.peerPort = peerPort;
		this.inQueue = inQueue;
	}
	
	@Override
	public void run() {
		// socketti yhdistää vertaiseen
		try (Socket socket = new Socket(peerIP, peerPort)) {
			System.out.printf("Connection established to %s, port %d%n", peerIP, peerPort);
			ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());			
			
			while (true) {
				//kuuntele viestejä
				Message message = (Message) inStream.readObject();
				//aina kun saadaan viesti, kirjoitetaan se inQueue:een
				inQueue.offer(message, 1, TimeUnit.SECONDS);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
	}
}