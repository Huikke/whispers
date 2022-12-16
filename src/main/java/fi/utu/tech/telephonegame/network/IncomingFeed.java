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
	
	private Socket socket;
	private TransferQueue<Object> inQueue;
	
	public IncomingFeed(Socket socket, TransferQueue<Object> inQueue) {
		this.socket = socket;
		this.inQueue = inQueue;
	}
	
	@Override
	public void run() {
		// socketti yhdistää vertaiseen
			//tästä tuli nyt hieman spagettia, mutta toivottavasti toimii
		try {
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