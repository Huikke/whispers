package fi.utu.tech.telephonegame.network;

//import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TransferQueue;

import fi.utu.tech.telephonegame.Message;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;

/*
 * Tämä luokka edustaa lähetys_yhteyttä vertaiseen 
 */
public class OutgoingFeed implements Runnable {
	
	Socket socket;
	private TransferQueue<Serializable> outQueue;
	
	public OutgoingFeed(Socket s,TransferQueue<Serializable> o) {
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
	
	public void send(Serializable message) {
		//lähetä viesti
		try {
			ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
			outStream.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
