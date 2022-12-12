package fi.utu.tech.telephonegame.network;

//import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TransferQueue;

import fi.utu.tech.telephonegame.Message;

import java.io.Serializable;

/*
 * Tämä luokka edustaa lähetys_yhteyttä vertaiseen 
 */
public class OutgoingFeed implements Runnable {
	
	Socket socket;
	private TransferQueue<Object> outQueue;
	
	public OutgoingFeed(Socket s,TransferQueue<Object> o) {
		this.socket = s;
		this.outQueue = o;
	}
	
	@Override
	public void run() {
		//kuunnellaan outQueue:ta ja lähetetään siihen ilmestyvät viestit eteenpäin
		while (true) {
			try {
				send((Message) outQueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void send(Serializable out) {
		//lähetä viesti
		//TODO
	}

}
