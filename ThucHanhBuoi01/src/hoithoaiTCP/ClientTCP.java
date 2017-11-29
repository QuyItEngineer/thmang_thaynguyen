package hoithoaiTCP;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {

	public static void main(String[] args) {
		try {
			Socket clientsocket = new Socket("192.168.2.12", 6666);
			System.out.println("Client created...");
			
			Scanner inFormServer = new Scanner(clientsocket.getInputStream());
			PrintStream outToServer = new PrintStream(clientsocket.getOutputStream());
			
			String textFormServer = inFormServer.nextLine();
			System.out.println("Server: "+textFormServer);
			
			Scanner inputFormKeyBoad = new Scanner(System.in);
			String stringClientToServer = inputFormKeyBoad.nextLine();
			
			outToServer.print(stringClientToServer);
			
			System.out.println("Server: "+inFormServer.nextLine());
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

}
