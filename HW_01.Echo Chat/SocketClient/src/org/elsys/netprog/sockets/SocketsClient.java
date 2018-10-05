package org.elsys.netprog.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SocketsClient {

	public static void main(String[] args) throws IOException {
		Socket echoSocket = null;
		try {
			    echoSocket = new Socket("localhost", 10001);
			    PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			    BufferedReader in = new BufferedReader(
			            new InputStreamReader(echoSocket.getInputStream()));
			    BufferedReader stdIn = new BufferedReader(
			            new InputStreamReader(System.in));
			    
			    new InputListener(stdIn, out, in).start();
			    
			    String serverInput;
			    while ((serverInput = in.readLine()) != null) {
			        System.out.println(serverInput);
			    }
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		} finally {
			if (echoSocket != null && !echoSocket.isClosed()) {
				echoSocket.close();
			}
		}
	}
	
	private final static class InputListener extends Thread {

		private BufferedReader stdIn;
		private PrintWriter out;
		private BufferedReader in;
		
		public InputListener(BufferedReader stdIn, PrintWriter out, BufferedReader in) {
			this.stdIn = stdIn;
			this.out = out;
			this.in = in;
		}
		
		@Override
		public void run() {
			String userInput;
		    try {
				while ((userInput = stdIn.readLine()) != null) {
				    out.println(userInput);
				    System.out.println(in.readLine());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}