package org.elsys.netprog.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class EchoServer {
	
	private static List<PrintWriter> OUTPUTS = new ArrayList<PrintWriter>();
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(Constants.PORT);
			
			while (true) {
				try {
					Socket clientSocket = serverSocket.accept();
				    System.out.println(Constants.CLIENT_CONNECTION_MESSAGE + clientSocket.getInetAddress());
				    
					new Thread(new ClientHandler(clientSocket)).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		} finally {
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			
			System.out.println(Constants.SERVER_CLOSED);
		}
	}
	
	private static final class ClientHandler implements Runnable {
        private BufferedReader in;
        private PrintWriter out;
		
		public ClientHandler(Socket clientSocket) throws IOException {
			this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);
		}
		
		@Override
		public void run() {
			try {
				OUTPUTS.add(this.out);
				
				String inputLine = this.in.readLine();
				
				while (!Constants.END_COMMAND.equals(inputLine)) {
					if(inputLine != null) {
						System.out.printf("User %d: %s%n", OUTPUTS.indexOf(out) + 1, inputLine);
						
						for(PrintWriter output : OUTPUTS) {
							output.printf("User %d: %s%n", OUTPUTS.indexOf(out) + 1, inputLine);
						}
					}
					
					inputLine = this.in.readLine();
				}
				
				System.out.printf("User %d left the chat room.", OUTPUTS.indexOf(out) + 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
