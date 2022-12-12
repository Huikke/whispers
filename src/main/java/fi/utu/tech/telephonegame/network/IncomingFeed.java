package fi.utu.tech.telephonegame.network;

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
	
	Socket socket;
	private TransferQueue<Object> inQueue = new LinkedTransferQueue<Object>();
	
	public IncomingFeed(Socket s,TransferQueue<Object> i) {
		this.socket = s;
		this.inQueue = i;
	}
	
	@Override
	public void run() {
		Message message = null;
		while (true) {
			//kuuntele viestejä
			//TODO
			//varmaan pitää setuppaa joku sisääntuleva streami siihen yhdistettyyn vertaiseen
			//ja sit toteuttaa se vastaava lähetys_streami siellä OutgoingFeed luokassa
			
			//aina kun saadaan viesti, kirjoitetaan se inQueue:een
			try {
				inQueue.offer(message, 1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
