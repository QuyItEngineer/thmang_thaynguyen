package hoithoaiTCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerTCP {

	public static void main(String[] args) {

		try {
			ServerSocket ssocket = new ServerSocket(6666);
			System.out.println("Server is starting...");
			
			Socket client = ssocket.accept();	
			
			System.out.println("Client connected to Server.");
			
			DataInputStream dataInput = new DataInputStream(client.getInputStream());
			DataOutputStream dataOut = new DataOutputStream(client.getOutputStream());
			
			dataOut.writeUTF("Hello Client... Input something...");
			
			String textDosomething=  dataInput.readUTF();
			System.out.println("Client: "+textDosomething);
			
//			String changeTextToCaps = textDosomething.toUpperCase();
//			outToClient.println("Return Caps: "+textDosomething);
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

}
